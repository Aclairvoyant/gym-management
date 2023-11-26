<template>

  <el-card class="page-container">
    <template #header>
      <div class="header">
        <h2>课程列表</h2>
        <div class="extra">
          <el-input v-model="searchName" placeholder="请输入课程名" style="width: 200px;" :prefix-icon="Search"
                    class="search-input"></el-input>
          <el-button type="primary" @click="searchCourse">搜索</el-button>
        </div>
      </div>
    </template>


    <router-view :key="route.fullPath"></router-view>
    <!-- 表单 -->
    <el-table v-loading="loading.value" :data="courses" style="width: 100%">
      <el-table-column prop="courseId" label="课程ID" width="150"></el-table-column>
      <el-table-column prop="courseName" label="课程名" width="180"></el-table-column>
      <el-table-column prop="coachId" label="教练ID" width="150"></el-table-column>
      <el-table-column prop="coachRealName" label="教练姓名" width="180"></el-table-column>
      <el-table-column prop="courseFee" label="金额/每课时" width="180"></el-table-column>

      <el-table-column prop="scheduleStart" label="课程开始时间" width="180">
        <template #default="{ row }">
          {{ formatDate(row.scheduleStart) }}
        </template>
      </el-table-column>
      <el-table-column prop="scheduleEnd" label="课程结束时间" width="180">
        <template #default="{ row }">
          {{ formatDate(row.scheduleEnd) }}
        </template>
      </el-table-column>

      <el-table-column label="操作" width="180" align="center" fixed="right">
        <template #default="{ row }">
          <el-button
              type="primary"
              size="mini"
              @click="handleEnroll(row)"
              :disabled="row.isEnrolled"
          >
            {{ row.isEnrolled ? '已报名' : '报名' }}
          </el-button>
          <el-button
              type="danger"
              size="mini"
              @click="handleRefund(row)"
              :disabled="row.isEnrolled"
          >
            退课
          </el-button>
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
import {
  addCourseService,
  deleteCourseService,
  editCourseService, getCoachListService,
  getCourseListService, refundCourseService,
  searchCourseService
} from "@/apis/course";
import router from "@/router";

const route = useRoute()

const courses = ref([]); // 定义 coachs
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
  fetchCourses();
};

// 处理当前页变化
const handleCurrentChange = (newPage) => {
  pagination.value.currentPage = newPage;
  fetchCourses();
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

// 获取课程列表，并支持分页
const fetchCourses = async () => {
  try {
    loading.value = true;
    const pageNum = pagination.value.currentPage;
    const pageSize = pagination.value.pageSize;

    //console.log(params)
    const response = await getCourseListService({pageNum, pageSize});
    courses.value = response.data.data.items;
    console.log(courses.value)
    pagination.value.total = response.data.data.total;
    //console.log(pagination.value.total)
    loading.value = false;
  } catch (error) {
    console.error('获取课程列表失败:', error);
  }
};

onMounted(fetchCourses);


// 搜索课程
const searchName = ref('');
const searchCourse = async () => {
  try {
    const courseName = searchName.value;
    const pageNum = pagination.value.currentPage;
    const pageSize = pagination.value.pageSize;
    const response = await searchCourseService({pageNum, pageSize, courseName});
    courses.value = response.data.data.items; // 使用搜索结果更新列表
  } catch (error) {
    console.error('搜索课程失败:', error);
  }
};

// 获取用户余额
import {userGetBalanceService} from '@/apis/user';

// checkUserBalance 方法用于检查用户余额是否足够
const checkUserBalance = async (courseFee) => {
  try {
    const response = await userGetBalanceService(); // 调用API获取用户余额
    const userBalance = response.data.data; // 假设返回数据中包含余额信息

    console.log('userBalance', userBalance)

    return userBalance >= courseFee; // 比较余额和课程费用
  } catch (error) {
    console.error('获取用户余额失败:', error);
    return false; // 出现错误时默认返回不足够
  }
};

import { payCourseFeeService } from '@/apis/course';
// payForCourse 方法用于调用支付接口
const payForCourse = async (courseId) => {
  try {
    console.log(courseId)
    //处理支付逻辑
    const response = await payCourseFeeService(courseId);
    return response.data; // 返回支付结果
  } catch (error) {
    console.error('支付课程费用失败:', error);
    throw new Error('支付失败'); // 抛出错误，可在调用时进行处理
  }
};

// import { enrollInCourseService } from '@/apis/course';
// // enrollCourseService 方法用于调用报名接口
// const enrollCourseService = async (courseId) => {
//   try {
//     //处理课程报名
//     const response = await enrollInCourseService(courseId);
//     return response.data; // 返回报名结果
//   } catch (error) {
//     console.error('报名课程失败:', error);
//     throw new Error('报名失败'); // 抛出错误，可在调用时进行处理
//   }
// };


// 报名
const handleEnroll = async (courses) => {
  try {
    // 检查余额逻辑（假设有一个方法 checkUserBalance 返回余额是否足够）
    const balanceEnough = await checkUserBalance(courses.courseFee);

    //console.log(courses.courseFee)

    if (!balanceEnough) {
      ElMessage.warning('余额不足，请先充值');
      await router.push('/recharge'); // 跳转到充值页面
      return;
    }

    // 调用支付接口，然后报名
    await payForCourse(courses.courseId);

    console.log(courses.courseId)
    //await enrollCourseService(courses.courseId);
    ElMessage.success('报名成功！');

  } catch (error) {
    console.error('报名失败:', error);
    ElMessage.error('报名失败');
  }
};

const canRefund = (scheduleStart) => {
  const now = Date.now();
  return now < scheduleStart;
};

// 退课
const handleRefund = async (course) => {
  try {
    // 检查是否可以退课（比如，检查当前时间是否在课程开始时间之前）
    if (!canRefund(course.scheduleStart)) {
      ElMessage.warning('课程已开始，无法退课');
      return;
    }

    //调用退款接口，然后退课
    //await refundForCourse(course.courseId);
    await refundCourseService(course.courseId);
    ElMessage.success('退课成功！');
  } catch (error) {
    console.error('退课失败:', error);
    ElMessage.error('退课失败');
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
