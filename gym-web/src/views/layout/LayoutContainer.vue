<template>
  <div class="common-layout">
    <el-container>
      <el-header>
        <div>
          <span>健身房会员管理系统</span>
        </div>
        <div>
          你好,<strong>{{
            userStore.user.userRealName
          }}</strong>
        </div>
        <el-dropdown placement="bottom-end" @command="handleCommand">
          <!-- 展示给用户，默认看到的 -->
          <span class="el-dropdown__box">
            <el-avatar :src="userStore.user.avatar" />
            <el-icon><CaretBottom /></el-icon>
          </span>

          <!-- 折叠的下拉部分 -->
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="profile" :icon="User"
              >基本资料</el-dropdown-item
              >
              <el-dropdown-item command="avatar" :icon="Crop"
              >更换头像</el-dropdown-item
              >
              <el-dropdown-item command="password" :icon="EditPen"
              >重置密码</el-dropdown-item
              >
              <el-dropdown-item command="logout" :icon="SwitchButton"
              >退出登录</el-dropdown-item
              >
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-header>
      <el-container class="layout-container">
        <el-aside width="240px">
          <el-row>
            <el-col>
              <div class="el-aside__logo">
                <!--                <img src="@/assets/logoc.png" alt="">-->
              </div>
              <el-menu class="el-menu"
                       background-color="#DCDCDC"
                       :default-active="route.path"
                       router
              >
                <el-menu-item index="/member/list">
                  <el-icon>
                    <UserFilled/>
                  </el-icon>
                  <span>会员管理</span>
                </el-menu-item>
                <el-menu-item index="/coach/list">
                  <el-icon>
                    <Bell />
                  </el-icon>
                  <span>教练管理</span>
                </el-menu-item>
                <el-menu-item index="/course/list">
                  <el-icon>
                    <document/>
                  </el-icon>
                  <span>课程管理</span>
                </el-menu-item>
                <el-menu-item index="/order/list">
                  <el-icon>
                    <Shop />
                  </el-icon>
                  <span>订单查询</span>
                </el-menu-item>
                <el-sub-menu index="/user">
                  <template #title>
                    <el-icon><UserFilled /></el-icon>
                    <span>个人中心</span>
                  </template>
                  <el-menu-item index="/user/profile">
                    <el-icon><User /></el-icon>
                    <span>基本资料</span>
                  </el-menu-item>
                  <el-menu-item index="/user/avatar">
                    <el-icon><Crop /></el-icon>
                    <span>更换头像</span>
                  </el-menu-item>
                  <el-menu-item index="/user/password">
                    <el-icon><EditPen /></el-icon>
                    <span>重置密码</span>
                  </el-menu-item>
                </el-sub-menu>
              </el-menu>
            </el-col>
          </el-row>
        </el-aside>
        <el-main>
          <router-view/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import {useUserStore} from '@/stores'
import {onMounted} from 'vue'
import {useRouter} from 'vue-router'
import { useRoute } from 'vue-router'
import {Crop, EditPen, SwitchButton, User} from "@element-plus/icons-vue";
import {ElMessageBox} from "element-plus";

const route = useRoute()

const userStore = useUserStore()
const router = useRouter()

onMounted(() => {
  userStore.getUser()
})

const handleCommand = async (key) => {
  if (key === 'logout') {
    // 退出操作
    await ElMessageBox.confirm('你确认要进行退出么', '温馨提示', {
      type: 'warning',
      confirmButtonText: '确认',
      cancelButtonText: '取消'
    })

    // 清除本地的数据 (token + user信息)
    userStore.removeToken()
    userStore.setUser({})
    await router.push('/login')
  } else {
    // 跳转操作
    await router.push(`/user/${key}`)
  }
}
</script>

<style scoped>
.layout-container {
  height: calc(100vh - 64px);

  .el-aside {
    background-color: #DCDCDC;

    &__logo {
      height: 120px;
      background: url('@/assets/logoc.png') no-repeat center / 120px auto;
    }

    .el-menu {
      border-right: none;
    }
  }
}

.el-header {
  background-color: #101010;

  display: flex;

  justify-content: space-between;

  padding-left: 40px;

  align-items: center;

  color: #fff;

  font-size: 22px;

  > div {

    display: flex;

    align-items: center;

    span {
      margin-left: 15px;
    }
  }

}

</style>
