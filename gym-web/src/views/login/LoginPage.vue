<script lang="js" setup>
import { userRegisterService, userLoginService } from '@/apis/user.ts'
import {User, Lock, Iphone, Sunrise} from '@element-plus/icons-vue'
import { ref, watch } from 'vue'
import { useUserStore } from '@/stores'
import { useRouter } from 'vue-router'
import {ElMessage} from "element-plus"
const isRegister = ref(false)
const form = ref()

// 整个的用于提交的form数据对象
const formModel = ref({
  userName: '',
  password: '',
  repassword: '',
  userPhone: '',
  datebirth: '',
  userRealName: '',
  sex: '',
  avatar: '',
})

const rules = {
  userName: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    {
      required: true,
      trigger: 'blur'
    }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    {
      required: true,
      trigger: 'blur'
    }
  ],
  repassword: [
    { required: true, message: '请再次输入密码', trigger: 'blur' },
    {
      required: true,
      trigger: 'blur'
    },
    {
      validator: (rule, value, callback) => {
        // 判断 value 和 当前 form 中收集的 password 是否一致
        if (value !== formModel.value.password) {
          callback(new Error('两次输入密码不一致'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ],
  userPhone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/,
      message: '手机号格式不正确', trigger: 'blur' },
    {
      required: true,
      trigger: 'blur'
    }
  ],
  userRealName: [
    { required: true, message: '请输入真实姓名', trigger: 'blur' },
    { pattern: /^[\u4E00-\u9FA5]{2,10}/,
      message: '真实姓名格式不正确', trigger: 'blur' },
    {
      required: true,
      trigger: 'blur'
    }
  ],
  dateBirth: [
    { required: true, message: '请选择出生日期', trigger: 'blur'},
    {
      required: true,
      trigger: 'blur'
    }
  ],
  sex: [
    { required: true, message: '请选择性别', trigger: 'blur' },
    {
      required: true,
      trigger: 'blur'
    }
  ],

}

const options = [
  {
    sex: '0',
    label: '男'
  },
  {
    sex: '1',
    label: '女'
  },
  {
    sex: '2',
    label: '保密'
  }
]

const handleAvatarSuccess = (res, file) => {
  formModel.value.imageUrl = URL.createObjectURL(file.raw)
}

const beforeAvatarUpload = (file) => {
  const isJPG = file.type === 'image/jpeg'
  const isLt2M = file.size / 1024 / 1024 < 2

  if (!isJPG) {
    ElMessage.error('上传头像图片只能是 JPG 格式!')
  }
  if (!isLt2M) {
    ElMessage.error('上传头像图片大小不能超过 2MB!')
  }
  return isJPG && isLt2M
}

const register = async () => {
  // 注册成功之前，先进行校验，校验成功 → 请求，校验失败 → 自动提示
  await form.value.validate()
  //formModel.value.datebirth = formModel.value.datebirth.toString();// 将日期转为字符串
  console.log(formModel.value.datebirth)
  //formModel.value.avatar = formModel.value.avatar || ''; // 头像地址
  //console.log(formModel.value.avatar)
  await userRegisterService(formModel.value)
  ElMessage.success('注册成功')
  isRegister.value = false
}

const userStore = useUserStore()
const router = useRouter()
const login = async () => {
  await form.value.validate()
  const res = await userLoginService(formModel.value)
  // console.log(res.data.token)
  // console.log(res.data.data)
  userStore.setToken(res.data.data)
  ElMessage.success('登录成功')
  router.push('/')
}

// 切换的时候，重置表单内容
watch(isRegister, () => {
  formModel.value = {
    userName: '',
    password: '',
    repassword: '',
    userPhone: '',
    datebirth: '',
    userRealName: '',
    sex: '',
    avatar: '',
  }
})
</script>

<template>

  <el-row class="login-page">
    <el-col :span="6" :offset="9" class="form">
      <!-- 注册相关表单 -->
      <el-form
          :model="formModel"
          :rules="rules"
          ref="form"
          size="large"
          autocomplete="off"
          v-if="isRegister"
      >
        <el-form-item>
          <h1>注册</h1>
        </el-form-item>
        <el-form-item><el-upload
            class="avatar-uploader"
            action="localhost:8080/register"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
        >
          <el-icon><Avatar /></el-icon>
          <img v-if="formModel.avatar" :src="formModel.avatar" class="avatar" />
          <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>

          <el-text>上传头像</el-text>
        </el-upload>
        </el-form-item>

        <el-form-item prop="userName">
          <el-input
              v-model="formModel.userName"
              :prefix-icon="User"
              placeholder="请输入用户名/登录名"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
              v-model="formModel.password"
              :prefix-icon="Lock"
              type="password"
              placeholder="请输入密码"
          ></el-input>
        </el-form-item>
        <el-form-item prop="repassword">
          <el-input
              v-model="formModel.repassword"
              :prefix-icon="Lock"
              type="password"
              placeholder="请再次输入密码"
          ></el-input>
        </el-form-item>
        <el-form-item prop="userPhone">
          <el-input
              v-model="formModel.userPhone"
              :prefix-icon="Iphone"
              placeholder="请输入手机号"
          ></el-input>
        </el-form-item>
        <el-form-item prop="userRealName">
          <el-input
              v-model="formModel.userRealName"
              :prefix-icon="Sunrise"
              placeholder="请输入真实姓名"
          ></el-input>
        </el-form-item>
        <el-form-item>
            <div>
              <el-date-picker
                  v-model="formModel.datebirth"
                  type="date"
                  placeholder="请选择出生日期"
                  value-format="YYYY-MM-DD"
              />
            </div>
          <el-select v-model="formModel.sex" class="m-2" placeholder="请选择性别">
            <el-option
                v-for="item in options"
                :key="item.sex"
                :label="item.label"
                :value="item.sex"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button
              @click="register"
              class="button"
              type="primary"
              auto-insert-space
          >
            注册
          </el-button>
        </el-form-item>
        <el-form-item class="flex">
          <el-link type="info" :underline="false" @click="isRegister = false">
            ← 返回
          </el-link>
        </el-form-item>
      </el-form>

      <!-- 登录相关表单 -->
      <el-form
          :model="formModel"
          :rules="rules"
          ref="form"
          size="large"
          autocomplete="off"
          v-else
      >
        <el-form-item>
          <h1>健身房会员管理系统 登录</h1>
        </el-form-item>
        <el-form-item prop="username">
          <el-input
              v-model="formModel.userName"
              :prefix-icon="User"
              placeholder="请输入用户名"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
              v-model="formModel.password"
              name="password"
              :prefix-icon="Lock"
              type="password"
              placeholder="请输入密码"
          ></el-input>
        </el-form-item>
        <el-form-item class="flex">
          <div class="flex">
            <el-checkbox>记住我</el-checkbox>
            <el-link type="primary" :underline="false">忘记密码？</el-link>
          </div>
        </el-form-item>
        <el-form-item>
          <el-button
              @click="login"
              class="button"
              type="primary"
              auto-insert-space
          >登录</el-button
          >
        </el-form-item>
        <el-form-item class="flex">
          <el-link type="info" :underline="false" @click="isRegister = true">
            注册 →
          </el-link>
        </el-form-item>
      </el-form>
    </el-col>
  </el-row>
</template>

<style lang="stylus" scoped>
.login-page {
  height: 100vh;
  background-color: #fff;

  .form {
    display: flex;
    flex-direction: column;
    justify-content: center;
    user-select: none;

    .button {
      width: 100%;
    }
    .flex {
      width: 100%;
      display: flex;
      justify-content: space-between;
    }
  }
}
</style>
