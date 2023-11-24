<template>

  <el-card class="page-container">
    <template #header>
      <div class="header">
        <h2>课程列表</h2>
        <div class="extra">
          <el-input v-model="searchName" placeholder="请输入课程名" style="width: 200px;" :prefix-icon="Search"
                    class="search-input"></el-input>
          <el-button type="primary" @click="searchCourse">搜索</el-button>
          <el-button type="primary" @click="addCourse">添加课程</el-button>
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

      <el-table-column prop="createTime" label="创建时间" width="180">
        <template #default="{ row }">
          {{ formatDate(row.createTime) }}
        </template>
      </el-table-column>
      <el-table-column prop="updateTime" label="更新时间" width="180">
        <template #default="{ row }">
          {{ formatDate(row.updateTime) }}
        </template>
      </el-table-column>


      <el-table-column label="操作" width="180" align="center" fixed="right">
        <template #default="{ row }">
          <el-button type="primary" size="mini" @click="handleEdit(row)" :icon="Edit" circle plain></el-button>
          <el-button type="danger" size="mini" @click="handleDelete(row)" :icon="Delete" circle plain></el-button>
          <el-button type="info" size="mini" @click="notifyMember(row)" :icon="Bell" circle plain></el-button>
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


  <!-- 编辑课程信息对话框 -->
  <el-dialog v-model="editDialogVisible" title="编辑课程信息">
    <el-form :model="editFormData">
      <el-form-item label="课程ID" :label-width="formLabelWidth">
        <el-input v-model="editFormData.courseId" disabled></el-input>
      </el-form-item>
      <el-form-item label="课程名" :label-width="formLabelWidth">
        <el-input v-model="editFormData.courseName" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="教练姓名" :label-width="formLabelWidth">
        <el-autocomplete
            v-model="addFormData.coachRealName"
            :fetch-suggestions="querySearch"
            clearable
            :debounce=0
            placeholder="选择教练(模糊查询)"
            @select="handleSelect"
        />
      </el-form-item>
      <el-form-item label="课程费用" :label-width="formLabelWidth">
        <el-input v-model="editFormData.courseFee" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="课程开始时间" :label-width="formLabelWidth">
        <el-date-picker
            v-model="editFormData.scheduleStart"
            type="datetime"
            placeholder="选择日期时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="课程结束时间" :label-width="formLabelWidth">
        <el-date-picker
            v-model="editFormData.scheduleEnd"
            type="datetime"
            placeholder="选择日期时间">
        </el-date-picker>
      </el-form-item>
    </el-form>

    <template #footer>
        <span class="dialog-footer">
          <el-button @click="editDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="updateCourseInfo">确认</el-button>
        </span>
    </template>
  </el-dialog>


  <!-- 添加课程信息对话框 -->
  <el-dialog v-model="addDialogVisible" title="添加课程信息">
    <el-form :model="addFormData">
      <el-form-item label="课程ID" :label-width="formLabelWidth">
        <el-input disabled placeholder="ID由系统自动生成"></el-input>
      </el-form-item>
      <el-form-item label="课程名" :label-width="formLabelWidth">
        <el-input v-model="addFormData.courseName" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="教练姓名" :label-width="formLabelWidth">
        <el-autocomplete
            v-model="addFormData.coachRealName"
            :fetch-suggestions="querySearch"
            clearable
            :debounce=0
            placeholder="选择教练(模糊查询)"
            @select="handleSelect"
        />
      </el-form-item>
      <el-form-item label="课程费用" :label-width="formLabelWidth">
        <el-input v-model="addFormData.courseFee" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="课程开始时间" :label-width="formLabelWidth">
        <el-date-picker
            v-model="addFormData.scheduleStart"
            type="datetime"
            placeholder="选择日期时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="课程结束时间" :label-width="formLabelWidth">
        <el-date-picker
            v-model="addFormData.scheduleEnd"
            type="datetime"
            placeholder="选择日期时间">
        </el-date-picker>
      </el-form-item>
    </el-form>
    <template #footer>
        <span class="dialog-footer">
          <el-button @click="addDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="addNewCourse">确认</el-button>
        </span>
    </template>
  </el-dialog>
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
  getCourseListService,
  searchCourseService
} from "@/apis/course";

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


const formLabelWidth = '100px';
const editDialogVisible = ref(false); // 控制编辑对话框的显示
const editFormData = ref({}); // 存储编辑表单数据
const addDialogVisible = ref(false); // 控制添加对话框的显示
const addFormData = ref({}); // 存储添加表单数据

// 打开编辑对话框
const handleEdit = (courses) => {
  editFormData.value = {...courses}; // 深拷贝会员信息到表单数据
  console.log(editFormData.value)
  editDialogVisible.value = true; // 显示对话框
  console.log(editDialogVisible.value)
};

// 打开添加对话框
const addCourse = () => {
  console.log('添加课程');
  addDialogVisible.value = true; // 显示对话框
};


// 更新信息的逻辑
const updateCourseInfo = async () => {
  console.log('更新课程信息', editFormData.value);
  try {
    await editCourseService(editFormData.value);

    ElMessage.success('课程信息更新成功');
  } catch (error) {
    console.error('更新课程信息失败:', error);
    ElMessage.error('更新失败');
  }

  editDialogVisible.value = false;
  await fetchCourses(); // 重新加载列表
};

// 删除课程
const handleDelete = (courses) => {
  console.log('删除课程操作', courses.courseName)
  ElMessageBox.confirm(`确定删除课程 ${courses.courseName} 吗?`, '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
      .then(async () => {
        // 用户确认删除
        try {
          await deleteCourseService({courseId: courses.courseId});
        } catch (error) {
          ElMessage.error('删除失败');
        }
        ElMessage.success('课程删除成功');
      })
      .catch(() => {
        // 用户取消删除
        ElMessage.info('取消删除操作');
      })
      .finally(async () => {
        // 不管用户是否取消，都会执行这里的代码
        await fetchCourses(); // 重新加载列表
      });
};


// 添加课程
const addNewCourse = async () => {
  try {
    const courseData = {
      courseName: addFormData.value.courseName,
      //coachId: addFormData.value.coachId,
      coachRealName: addFormData.value.coachRealName,
      courseFee: addFormData.value.courseFee,
      scheduleStart: addFormData.value.scheduleStart,
      scheduleEnd: addFormData.value.scheduleEnd,
    };
    console.log('添加课程信息', courseData);
    await addCourseService(courseData);
    ElMessage.success('课程添加成功');

    addFormData.value = {}; // 清空表单数据
    courseData.value = {};

    addDialogVisible.value = false;
    await fetchCourses(); // 重新加载列表
  } catch (error) {
    console.error('添加课程失败:', error);
    ElMessage.error('添加失败');
  }
};

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

const coachsList = ref([]);

// 当组件被加载时，获取所有教练的数据
onMounted(async () => {
  try {
    const response = await getCoachListService();
    coachsList.value = response.data.data; // 假设教练信息在items数组中
    console.log(coachsList.value)
  } catch (error) {
    console.error('获取教练列表失败:', error);
  }
});

// 根据输入查询匹配的教练姓名
// const querySearch = (queryString, cb) => {
//   console.log("查询教练:", queryString);
//   const results = coachsList.value.filter(coach =>
//       coach.coachRealName.toLowerCase().includes(queryString.toLowerCase())
//   ).map(coach => coach.coachRealName);
//   console.log("结果:", results);
//   cb(results);
// }

const querySearch = async (queryString, cb) => {
  try {
    console.log("查询教练:", queryString);
    const response = await getCoachListService();
    const coachNames = response.data.data.map(coach => ({ value: coach.coachRealName }));
    // 过滤和搜索词匹配的结果
    const results = coachNames.filter(coach => coach.value.toLowerCase().includes(queryString.toLowerCase()));
    console.log("结果:", results);
    cb(results); // 返回结果
  } catch (error) {
    console.error('获取教练列表失败:', error);
    cb([]); // 发生错误时返回空数组
  }
}


// 当用户选择一个建议时的处理函数
const handleSelect = (item) => {
  console.log('选择的教练:', item);
  // 这里可以根据选择的项目更新其他表单项
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
