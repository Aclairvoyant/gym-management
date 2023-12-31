<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <h2>会员账单</h2>
        <div>
          <el-button type="primary" @click="recharge">充值余额</el-button>
        </div>
      </div>
    </template>

    <!-- 表格展示账单数据 -->
    <el-table :data="bills" style="width: 100%">
      <el-table-column prop="paymentId" label="账单ID" width="180"></el-table-column>
      <el-table-column prop="courseName" label="课程名" width="200"></el-table-column>
      <el-table-column prop="paymentDate" label="日期时间" width="180">
        <template #default="{ row }">
          {{ formatDate(row.paymentDate) }}
        </template>
      </el-table-column>
      <el-table-column prop="amount" label="金额(元）" width="180"></el-table-column>
      <el-table-column prop="paymentType" label="类型" width="180"></el-table-column>
      <el-table-column prop="memberFee" label="总余额(元)" width="180"></el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <div class="el-page">
      <el-config-provider :locale="language">
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="pagination.currentPage"
            :page-sizes="[10, 20, 30, 40, 50]"
            :page-size="pagination.pageSize"
            :total="pagination.total"
            :background="true"
            layout="jumper, total, sizes, prev, pager, next"
            style="margin-top: 20px;">
        </el-pagination>
      </el-config-provider>
    </div>
  </el-card>

  <!-- 充值对话框 -->
  <el-dialog title="充值" v-model="rechargeDialogVisible">
    <el-form :model="rechargeForm">
      <el-form-item label="充值金额">
        <el-input v-model="rechargeForm.amount" placeholder="请输入充值金额"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="rechargeDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleRecharge">确定</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import {fetchBills, getBillListService, rechargeService} from '@/apis/bill'; // 获取账单数据
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import {getCourseListService} from "@/apis/course";
import {ElMessage} from "element-plus";

const language = ref(zhCn); // 定义语言
const bills = ref([]);
const pagination = ref({
  currentPage: 1,
  pageSize: 10,
  total: 0
});

const rechargeDialogVisible = ref(false);
const rechargeForm = ref({ amount: '' });

// 充值按钮点击事件
const recharge = () => {
  rechargeDialogVisible.value = true;
};

// 处理充值
const handleRecharge = async () => {
  try {
    const response = await rechargeService(rechargeForm.value.amount);
    if (response.data.success) {
      // 充值成功处理
      ElMessage.success('充值成功');
      rechargeDialogVisible.value = false;
      rechargeForm.value.amount = ''; // 清空充值金额
      // 可能需要刷新账单或余额信息
    } else {
      // 充值失败处理
      ElMessage.error('充值失败');
    }
  } catch (error) {
    console.error('充值过程中发生错误:', error);
    ElMessage.error('充值失败');
  }
};

const formatDate = (timestamp) => {
  return new Intl.DateTimeFormat('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: 'numeric',
    minute: 'numeric'
  }).format(new Date(timestamp));
};

const fetchMemberBills = async () => {
  try {
    const pageNum = pagination.value.currentPage;
    const pageSize = pagination.value.pageSize;

    //console.log(params)
    const response = await getBillListService({pageNum, pageSize});
    bills.value = response.data.data.items;
    console.log(bills.value)
    pagination.value.total = response.data.data.total;
  } catch (error) {
    console.error('获取账单列表失败:', error);
  }
};
const handleSizeChange = newSize => {
  pagination.value.pageSize = newSize;
  fetchMemberBills();
};

const handleCurrentChange = newPage => {
  pagination.value.currentPage = newPage;
  fetchMemberBills();
};

onMounted(fetchMemberBills);

</script>

<style scoped>
.page-container {
  min-height: 100%;
  box-sizing: border-box;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.el-page {
  display: flex;
  justify-content: end;
  align-items: center;
}

</style>
