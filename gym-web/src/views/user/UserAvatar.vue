
<template>
  <page-container title="更换头像">
    <el-upload
      ref="uploadRef"
      :auto-upload="false"
      class="avatar-uploader"
      :show-file-list="false"
      :on-change="onSelectFile"
    >
      <img v-if="imgUrl" :src="imgUrl" class="avatar" />
      <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
    </el-upload>

    <br />

    <el-button
      @click="uploadRef.$el.querySelector('input').click()"
      type="primary"
      :icon="Plus"
      size="large"
      >选择图片</el-button
    >
    <el-button
      @click="onUpdateAvatar"
      type="success"
      :icon="Upload"
      size="large"
      >上传头像</el-button
    >
  </page-container>
</template>

<script setup>
import { ref } from 'vue';
import { Plus, Upload } from '@element-plus/icons-vue';
import { useUserStore } from '@/stores';
import { userUpdateAvatarService } from '@/apis/user';
import { ElMessage } from "element-plus";

const userStore = useUserStore();
const imgUrl = ref(userStore.user.avatar);
const selectedFile = ref(null); // 用于存储选择的文件
const uploadRef = ref();

// 图像选择并预览
const onSelectFile = (uploadEvent) => {
  // Element UI 的 Upload 组件可能以不同的格式传递文件
  // 可能是 uploadEvent.file 或 uploadEvent.raw
  const file = uploadEvent.file ? uploadEvent.file : uploadEvent.raw;

  // 检查 file 是否有效
  if (!file || !(file instanceof Blob)) {
    console.error('无效的文件类型:', file);
    return;
  }

  selectedFile.value = file;  // 保存选中的文件
  const reader = new FileReader();
  reader.readAsDataURL(file);
  reader.onload = () => {
    imgUrl.value = reader.result;
  }
};


// 上传头像
const onUpdateAvatar = async () => {
  if (selectedFile.value) {
    const formData = new FormData();
    formData.append('file', selectedFile.value);
    formData.append("userId", userStore.user.userId);

    try {
      const response = await userUpdateAvatarService(formData);
      const updatedAvatarUrl = response.data;  // 假设后端返回新的头像URL

      // 更新用户信息
      await userStore.getUser();
      ElMessage.success('头像更新成功');

      // 更新imgUrl，如果需要
      imgUrl.value = updatedAvatarUrl;
    } catch (error) {
      console.error('头像更新失败:', error);
      //ElMessage.error('头像更新失败');
    }
  } else {
    ElMessage.warning('请选择一个头像');
  }
};

</script>


<style lang="scss" scoped>
.avatar-uploader {
  :deep() {
    .avatar {
      width: 278px;
      height: 278px;
      display: block;
    }
    .el-upload {
      border: 1px dashed var(--el-border-color);
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
      transition: var(--el-transition-duration-fast);
    }
    .el-upload:hover {
      border-color: var(--el-color-primary);
    }
    .el-icon.avatar-uploader-icon {
      font-size: 28px;
      color: #8c939d;
      width: 278px;
      height: 278px;
      text-align: center;
    }
  }
}
</style>
