<template>

  <el-card class="page-container">
    <template #header>
      <div class="header">
        <h2>订单列表</h2>
        <div class="extra">
          <el-input v-model="searchName" placeholder="请输入课程名" style="width: 200px;" :prefix-icon="Search"
                    class="search-input"></el-input>
          <el-button type="primary" @click="searchOrder">搜索</el-button>
        </div>
      </div>
    </template>


    <router-view :key="route.fullPath"></router-view>
    <!-- 表单 -->
    <el-table v-loading="loading.value" :data="orders" style="width: 100%">
      <el-table-column prop="bookingId" label="预定ID" width="150"></el-table-column>
      <el-table-column prop="courseName" label="课程名" width="180"></el-table-column>
      <el-table-column prop="userRealName" label="用户姓名" width="180"></el-table-column>
      <el-table-column prop="coachRealName" label="教练姓名" width="180"></el-table-column>
      <el-table-column prop="paymentId" label="支付ID" width="150"></el-table-column>
      <el-table-column prop="paymentType" label="支付方式" width="150"></el-table-column>
      <el-table-column prop="paymentStatus" label="支付状态" width="150">
        <template #default="{ row }">
          {{ statusText(row.paymentStatus) }}
        </template>
      </el-table-column>

      <el-table-column prop="amount" label="金额" width="150"></el-table-column>

      <el-table-column prop="bookingDate" label="预定时间" width="180">
        <template #default="{ row }">
          {{ formatDate(row.bookingDate) }}
        </template>
      </el-table-column>
      <el-table-column prop="paymentDate" label="支付时间" width="180">
        <template #default="{ row }">
          {{ formatDate(row.paymentDate) }}
        </template>
      </el-table-column>

      <template #empty>
        <el-empty description="没有数据"/>
      </template>
    </el-table>


    <!-- 分页 -->
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
</template>

<script setup>
import {ElMessage, ElMessageBox} from "element-plus";
import {ref, onMounted} from "vue";
import {Bell, Delete, Edit, Search} from "@element-plus/icons-vue";
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import {useRoute} from 'vue-router'
import {getOrderListService, searchOrderService} from "@/apis/order";

const route = useRoute()

const orders = ref([]);
const language = ref(zhCn); // 定义语言
const loading = ref(false); // 控制加载状态的显示

const statusText = (statusCode) => {
  const status = {
    '0': '未支付',
    '1': '已支付',
    '2': '已退款'
  };
  return status[statusCode] || '未知';
};

// 分页数据
const pagination = ref({
  currentPage: 1,
  pageSize: 10,
  total: 0
});

// 处理每页显示条数变化
const handleSizeChange = (newSize) => {
  pagination.value.pageSize = newSize;
  fetchOrders();
};

// 处理当前页变化
const handleCurrentChange = (newPage) => {
  pagination.value.currentPage = newPage;
  fetchOrders();
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

// 获取订单列表，并支持分页
const fetchOrders = async () => {
  try {
    loading.value = true;
    const pageNum = pagination.value.currentPage;
    const pageSize = pagination.value.pageSize;

    //console.log(params)
    const response = await getOrderListService({pageNum, pageSize});
    orders.value = response.data.data.items;
    console.log(orders.value)
    pagination.value.total = response.data.data.total;
    //console.log(pagination.value.total)
    loading.value = false;
  } catch (error) {
    console.error('获取订单列表失败:', error);
  }
};

onMounted(fetchOrders);


// 搜索订单
const searchName = ref('');
const searchOrder = async () => {
  try {
    const courseName = searchName.value;
    const pageNum = pagination.value.currentPage;
    const pageSize = pagination.value.pageSize;
    const response = await searchOrderService({pageNum, pageSize, courseName});
    orders.value = response.data.data.items; // 使用搜索结果更新列表
  } catch (error) {
    console.error('搜索订单失败:', error);
  }
};

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

.search-input {
  margin-right: 10px;
}

</style>
