<template>
  <el-card class="page-container">
    <template #header>
      <div class="header">
        <h2>日志列表</h2>
        <div class="extra">
          <el-input v-model="searchEverything" placeholder="请输入查询内容" style="width: 200px;" :prefix-icon="Search"
                    class="search-input"></el-input>
          <el-button type="primary" @click="searchLog">搜索</el-button>
        </div>
      </div>
    </template>

    <!-- 表单 -->
    <el-table v-loading="loading.value" :data="logs" style="width: 100%">
      <el-table-column prop="logId" label="日志ID" width="150"></el-table-column>
      <el-table-column prop="userId" label="用户ID" width="150"></el-table-column>
      <el-table-column prop="userName" label="用户名" width="180"></el-table-column>
      <el-table-column prop="actionType" label="操作类型" width="180"></el-table-column>
      <el-table-column prop="requestMethod" label="请求方法" width="180"></el-table-column>
      <el-table-column prop="requestUri" label="请求URI" width="180"></el-table-column>
      <el-table-column prop="actionDate" label="操作时间" width="180">
        <template #default="{ row }">
          {{ formatDate(row.actionDate) }}
        </template>
      </el-table-column>
      <el-table-column prop="requestIp" label="请求IP" width="150"></el-table-column>
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
import {getLogListService, searchLogService} from "@/apis/log";

const route = useRoute()

const logs = ref([]);
const language = ref(zhCn); // 定义语言
const loading = ref(false); // 控制加载状态的显示


// 分页数据
const pagination = ref({
  currentPage: 1,
  pageSize: 10,
  total: 0
});

// 处理每页显示条数变化
const handleSizeChange = (newSize) => {
  pagination.value.pageSize = newSize;
  fetchLogs();
};

// 处理当前页变化
const handleCurrentChange = (newPage) => {
  pagination.value.currentPage = newPage;
  fetchLogs();
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

// 获取日志列表，并支持分页
const fetchLogs = async () => {
  try {
    loading.value = true;
    const pageNum = pagination.value.currentPage;
    const pageSize = pagination.value.pageSize;

    //console.log(params)
    const response = await getLogListService({pageNum, pageSize});
    logs.value = response.data.data.items;
    console.log(logs.value)
    pagination.value.total = response.data.data.total;
    //console.log(pagination.value.total)
    loading.value = false;
  } catch (error) {
    console.error('获取日志列表失败:', error);
  }
};

onMounted(fetchLogs);


// 搜索日志
const searchEverything = ref('');
const searchLog = async () => {
  try {
    const query = searchEverything.value;
    const pageNum = pagination.value.currentPage;
    const pageSize = pagination.value.pageSize;
    const response = await searchLogService({pageNum, pageSize, query});
    logs.value = response.data.data.items; // 使用搜索结果更新列表
  } catch (error) {
    console.error('搜索日志失败:', error);
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

