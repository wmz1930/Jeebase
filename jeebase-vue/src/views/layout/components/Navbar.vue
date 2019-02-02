<template>
  <div class="navbar">
    <hamburger :toggle-click="toggleSideBar" :is-active="sidebar.opened" class="hamburger-container"/>

    <breadcrumb class="breadcrumb-container"/>

    <div class="right-menu">
      <template v-if="device!=='mobile'">
        <error-log class="errLog-container right-menu-item"/>

        <el-tooltip :content="$t('navbar.screenfull')" effect="dark" placement="bottom">
          <screenfull class="screenfull right-menu-item"/>
        </el-tooltip>
      </template>

      <el-dropdown class="avatar-container right-menu-item" trigger="click">
        <div class="avatar-wrapper">
          <img :src="avatar+'?imageView2/1/w/80/h/80'" class="user-avatar">
          <label class="user-account">{{ userAccount }}</label><i class="el-icon-caret-bottom"/>
        </div>
        <el-dropdown-menu slot="dropdown">
          <router-link to="/user/info">
            <el-dropdown-item>
              个人信息
            </el-dropdown-item>
          </router-link>
          <a href="javascript:;" @click="handleUpdatePwd">
            <el-dropdown-item>
              修改密码
            </el-dropdown-item>
          </a>
          <el-dropdown-item divided>
            <span style="display:block;" @click="logout">{{ $t('navbar.logOut') }}</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <el-dialog :visible.sync="dialogFormVisible" title="修改密码" width="25%">
      <el-form ref="changePwdForm" :model="changePwdForm" :rules="rules" label-width="100px" class="changePwdForm" style="width: 400px; margin-left:50px;">
        <el-form-item label="旧密码" prop="oldPwd">
          <el-input v-model="changePwdForm.oldPwd" placeholder="输入旧密码" maxlength="32" type="password"/>
        </el-form-item>
        <el-form-item label="新密码" prop="newPwd">
          <el-input v-model="changePwdForm.newPwd" placeholder="输入新密码" maxlength="32" type="password"/>
        </el-form-item>
        <el-form-item label="新密码" prop="newPwdRe">
          <el-input v-model="changePwdForm.newPwdRe" placeholder="请再次输入新密码" maxlength="32" type="password"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">{{ $t('table.cancel') }}</el-button>
        <el-button type="primary" @click="updatePwdData">{{ $t('table.confirm') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import ErrorLog from '@/components/ErrorLog'
import Screenfull from '@/components/Screenfull'
import SizeSelect from '@/components/SizeSelect'
import LangSelect from '@/components/LangSelect'
import ThemePicker from '@/components/ThemePicker'
import { updatePwd, queryUserInfo } from '@/api/system/user'

export default {
  components: {
    Breadcrumb,
    Hamburger,
    ErrorLog,
    Screenfull,
    SizeSelect,
    LangSelect,
    ThemePicker
  },
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入新密码'))
      } else {
        if (this.changePwdForm.newPwdRe !== '') {
          this.$refs.changePwdForm.validateField('newPwdRe')
        }
        callback()
      }
    }
    var validatePassRe = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入新密码'))
      } else if (value !== this.changePwdForm.newPwd) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      dialogFormVisible: false,
      changePwdForm: {
        oldPwd: '',
        newPwd: '',
        newPwdRe: ''
      },
      rules: {
        oldPwd: [
          { required: true, message: '请输入旧密码', trigger: 'blur' },
          { min: 6, max: 32, message: '长度在 6 到 32 个字符', trigger: 'blur' }
        ],
        newPwd: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
          {
            min: 6,
            max: 32,
            message: '长度在 6 到 32 个字符',
            trigger: 'blur'
          },
          { validator: validatePass, trigger: 'blur' }
        ],
        newPwdRe: [
          { required: true, message: '请再次输入新密码', trigger: 'blur' },
          {
            min: 6,
            max: 32,
            message: '长度在 6 到 32 个字符',
            trigger: 'blur'
          },
          { validator: validatePassRe, trigger: 'blur' }
        ]
      },
      downloadLoading: false,
      userAccount: '',
      headImgUrl: ''
    }
  },
  computed: {
    ...mapGetters(['sidebar', 'name', 'avatar', 'device'])
  },
  created() {
    this.queryData()
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('toggleSideBar')
    },
    logout() {
      this.$store.dispatch('LogOut').then(() => {
        location.reload() // In order to re-instantiate the vue-router object to avoid bugs
      })
    },
    resetUpdatePwdForm() {
      this.changePwdForm = {
        oldPwd: '',
        newPwd: '',
        newPwdRe: ''
      }
    },
    handleUpdatePwd() {
      this.resetUpdatePwdForm()
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['changePwdForm'].clearValidate()
      })
    },
    updatePwdData() {
      this.$refs['changePwdForm'].validate(valid => {
        if (valid) {
          updatePwd(this.changePwdForm).then(() => {
            this.dialogFormVisible = false
            this.$message({
              message: '更新成功',
              type: 'success'
            })
            this.logout()
          })
        }
      })
    },
    queryData() {
      queryUserInfo().then(res => {
        this.userAccount = res.data.userAccount
        this.headImgUrl = res.data.headImgUrl
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.navbar {
  height: 50px;
  line-height: 50px;
  border-radius: 0px !important;
  .hamburger-container {
    line-height: 58px;
    height: 50px;
    float: left;
    padding: 0 10px;
  }
  .breadcrumb-container {
    float: left;
  }
  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }
  .right-menu {
    float: right;
    height: 100%;
    &:focus {
      outline: none;
    }
    .right-menu-item {
      display: inline-block;
      margin: 0 8px;
    }
    .screenfull {
      height: 20px;
    }
    .international {
      vertical-align: top;
    }
    .theme-switch {
      vertical-align: 15px;
    }
    .avatar-container {
      height: 50px;
      margin-right: 30px;
      .avatar-wrapper {
        cursor: pointer;
        margin-top: 5px;
        position: relative;
        .user-avatar {
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }
        .el-icon-caret-bottom {
          position: absolute;
          right: -22px;
          top: 9px;
          font-size: 22px;
        }
        .user-account {
          position: relative;
          cursor: pointer;
          top: -15px;
        }
      }
    }
  }
}
</style>
