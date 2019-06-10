<template>
  <div class="login-container">
    <background />
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on" label-position="left">

      <div class="title-container">
        <img :src="logoImg" class="logo-img">
        <h3 class="title">{{ $t('login.title') }}</h3>
        <!--
        <lang-select class="set-language"></lang-select>-->
      </div>

      <el-form-item prop="userAccount">
        <span class="svg-container svg-container_login">
          <svg-icon icon-class="user" />
        </span>
        <el-input
          ref="userAccount"
          v-model="loginForm.userAccount"
          :placeholder="$t('login.userAccount')"
          name="userAccount"
          type="text"
          tabindex="1"
          auto-complete="on"
        />
      </el-form-item>

      <el-tooltip v-model="capsTooltip" content="Caps lock is On" placement="right" manual>
        <el-form-item prop="userPassword">
          <span class="svg-container">
            <svg-icon icon-class="password" />
          </span>
          <el-input
            :key="passwordType"
            ref="userPassword"
            v-model="loginForm.userPassword"
            :type="passwordType"
            :placeholder="$t('login.userPassword')"
            tabindex="2"
            name="userPassword"
            auto-complete="on"
            @keyup.native="checkCapslock"
            @blur="capsTooltip = false"
            @keyup.enter.native="handleLogin"
          />
          <span class="show-pwd" @click="showPwd">
            <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
          </span>
        </el-form-item>
      </el-tooltip>
      <el-col :span="15">
        <el-form-item prop="vcode">
          <span class="svg-container">
            <svg-icon icon-class="password" />
          </span>
          <el-input
            v-model="loginForm.vcode"
            :placeholder="$t('login.vcode')"
            name="vcode"
            type="text"
            tabindex="3"
            auto-complete="on"
            @keyup.enter.native="handleLogin"
          />
        </el-form-item>
      </el-col>
      <el-col :span="9">
        <img :src="vcodeImg" class="v-code-img" @click="changeImgCode">
      </el-col>

      <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:30px;" @click.native.prevent="handleLogin">{{ $t('login.logIn') }}</el-button>

      <div style="position:relative">
        <div class="tips">
          Jeebase 前后端分离开源整合框架
        </div>
        <div class="tips">
          Copyright © 2012-2018 Jeebase. All Rights Reserved.
        </div>
        <el-input v-show="false" v-model="loginForm.verkey" name="verkey" type="hidden" />
        <!--
        <el-button class="thirdparty-button" type="primary" @click="showDialog=true">
          {{ $t('login.thirdparty') }}
        </el-button>-->
      </div>
    </el-form>
    <el-dialog :title="$t('login.thirdparty')" :visible.sync="showDialog">
      {{ $t('login.thirdpartyTips') }}
      <br>
      <br>
      <br>
      <social-sign />
    </el-dialog>
  </div>
</template>

<script>
import { isvalidUserAccount } from '@/utils/validate'
import Background from '@/components/Background'
import SocialSign from './components/SocialSignin'
import logo from '@/assets/images/logo.png'

export default {
  name: 'Login',
  components: { SocialSign, Background },
  data() {
    const validateUserAccount = (rule, value, callback) => {
      if (!isvalidUserAccount(value)) {
        callback(new Error('请输入账号'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (value.length < 6) {
        callback(new Error('请输入不小于6位的密码'))
      } else {
        callback()
      }
    }
    const validateVCode = (rule, value, callback) => {
      if (value.length < 5) {
        callback(new Error('请输入图片验证码'))
      } else {
        callback()
      }
    }
    return {
      loginForm: {
        userAccount: '',
        userPassword: '',
        vcode: '',
        verkey: ''
      },
      loginRules: {
        userAccount: [{ required: true, trigger: 'blur', validator: validateUserAccount }],
        userPassword: [{ required: true, trigger: 'blur', validator: validatePassword }],
        vcode: [{ required: true, trigger: 'blur', validator: validateVCode }]
      },
      vcodeImg: this.imgCode(),
      passwordType: 'password',
      capsTooltip: false,
      loading: false,
      showDialog: false,
      logoImg: logo,
      redirect: undefined
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        const query = route.query
        if (query) {
          this.redirect = query.redirect
          this.otherQuery = this.getOtherQuery(query)
        }
      },
      immediate: true
    }
  },
  created() {
    // window.addEventListener('storage', this.afterQRScan)
  },
  mounted() {
    if (this.loginForm.username === '') {
      this.$refs.username.focus()
    } else if (this.loginForm.password === '') {
      this.$refs.password.focus()
    }
  },
  destroyed() {
    // window.removeEventListener('storage', this.afterQRScan)
  },
  methods: {
    gRandom() {
      return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1)
    },
    guid() {
      this.verkey = (this.gRandom() + this.gRandom() + '-' + this.gRandom() + '-' + this.gRandom() + '-' + this.gRandom() + '-' + this.gRandom() + this.gRandom() + this.gRandom())
      return this.verkey
    },
    imgCode() {
      return process.env.VUE_APP_BASE_API + '/auth/vcode?codeKey=' + this.guid() + '&n=' + Math.random()
    },
    changeImgCode() {
      this.vcodeImg = this.imgCode()
    },
    checkCapslock({ shiftKey, key } = {}) {
      if (key && key.length === 1) {
        if (shiftKey && (key >= 'a' && key <= 'z') || !shiftKey && (key >= 'A' && key <= 'Z')) {
          this.capsTooltip = true
        } else {
          this.capsTooltip = false
        }
      }
      if (key === 'CapsLock' && this.capsTooltip === true) {
        this.capsTooltip = false
      }
    },
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = ''
      } else {
        this.passwordType = 'password'
      }
      this.$nextTick(() => {
        this.$refs.password.focus()
      })
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          this.loginForm.verkey = this.verkey
          this.$store.dispatch('user/login', this.loginForm).then(() => {
            this.loading = false
            this.$router.push({ path: this.redirect || '/' })
          }).catch(() => {
            this.loading = false
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    getOtherQuery(query) {
      return Object.keys(query).reduce((acc, cur) => {
        if (cur !== 'redirect') {
          acc[cur] = query[cur]
        }
        return acc
      }, {})
    }
    // afterQRScan() {
    //   if (e.key === 'x-admin-oauth-code') {
    //     const code = getQueryObject(e.newValue)
    //     const codeMap = {
    //       wechat: 'code',
    //       tencent: 'code'
    //     }
    //     const type = codeMap[this.auth_type]
    //     const codeName = code[type]
    //     if (codeName) {
    //       this.$store.dispatch('LoginByThirdparty', codeName).then(() => {
    //         this.$router.push({ path: this.redirect || '/' })
    //       })
    //     } else {
    //       alert('第三方登录失败')
    //     }
    //   }
    // }
  }
}
</script>

<style lang="scss">
/* 修复input 背景不协调 和光标变色 */
/* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

$bg:#283443;
$light_gray:#fff;
$cursor: #fff;

@supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
  .login-container .el-input input {
    color: $cursor;
  }
}

/* reset element-ui css */
.login-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;

    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      caret-color: $cursor;

      &:-webkit-autofill {
        box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: $cursor !important;
      }
    }
  }

  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}
</style>

<style lang="scss" scoped>
$bg:#2d3a4b;
$dark_gray:#889aa4;
$light_gray:#eee;
$background: '';

.login-container {
  position: fixed;
  height: 100%;
  width: 100%;
  background:url($background) no-repeat center fixed;
  background-size:100%;
  .login-form {
    position: absolute;
    left:0;
    right: 0;
    width: 520px;
    padding: 35px 35px 15px 35px;
    margin: 8% auto;
    background: rgba(109, 109, 109, 0.23);
    border: 0px solid rgb(221, 222, 225);
    box-shadow: 1px 1px 50px rgba(0,0,0,.3);
  }

  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;

    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }

  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }

  .title-container {
    position: relative;

    .title {
      font-size: 26px;
      color: $light_gray;
      margin: 0px auto 40px auto;
      text-align: left;
      font-weight: bold;
      padding-top: 12px;
    }

  }

  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }

  .thirdparty-button {
    position: absolute;
    right: 0;
    bottom: 6px;
  }
  .v-code-img {
    height: 47px;
    float: right;
    margin-top: 2px;
    border-radius: 5px;
    cursor: pointer;
    opacity: 0.6;
    filter: alpha(opacity=60);
  }
  .logo-img {
    height: 47px;
    float: left;
    margin-top: 2px;
    border-radius: 5px;
    cursor: pointer;
    margin-left: 76px;
  }

  @media only screen and (max-width: 470px) {
    .thirdparty-button {
      display: none;
    }
  }
}
</style>
