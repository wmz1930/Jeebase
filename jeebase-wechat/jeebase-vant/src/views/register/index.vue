<template>
  <div class="register">
    <background/>
    <div class="register_form">
      <van-cell-group>
        <van-field
          v-validate="'required|max:11|phone'"
          v-model.trim="registerForm.mobile"
          :max="11"
          :error="errors.has('group-sms.mobile')"
          :error-message="errors.first('group-sms.mobile')"
          data-vv-scope="group-sms"
          name="mobile"
          clearable
          label="手机号码"
          left-icon="manager"
          placeholder="请输入手机号"
          maxlength="11"
        />
        <van-cell-group>
          <van-field
            v-validate="'required|max:6|min:6'"
            v-model="registerForm.smsCode"
            :min="6"
            :max="6"
            :error="errors.has('smsCode')"
            :error-message="errors.first('smsCode')"
            name="smsCode"
            center
            clearable
            label="手机验证码"
            left-icon="comment"
            placeholder="请输入验证码"
            maxlength="6"
          >
            <van-button slot="button" :disabled="isDisabled" plain size="small" type="warning" @click.native="sendMsg">{{ buttonName }}</van-button>
          </van-field>
        </van-cell-group>
        <van-field
          v-validate="'required|max:32|min:8'"
          v-model.trim="registerForm.userPassword"
          :min="6"
          :max="32"
          :error="errors.has('userPassword')"
          :error-message="errors.first('userPassword')"
          name="userPassword"
          type="password"
          label="密码"
          left-icon="lock"
          placeholder="8-32位数字+字母或符号组成"
          maxlength="32"
        />
      </van-cell-group>
      <van-row>
        <van-col span="24" class="register-btn">
          <van-button type="warning" size="large" @click.native="handleRegister">立即注册</van-button>
        </van-col>
      </van-row>
      <van-row>
        <router-link :to="loginUrl" class="back-login-btn">返回登录页面</router-link>
      </van-row>
      <van-row class="copyright">
        <van-col span="24">
          <span class="copyright-text">©2019 Jeebase</span>
        </van-col>
      </van-row>
    </div>
  </div>
</template>
<script>
import Background from '@/components/Background'
import { Dialog } from 'vant'
import { sendRegisterSms, registerNormal } from '@/api/register'
import '@/styles/register.scss'
export default {
  components: {
    Background
  },
  data() {
    return {
      registerForm: {
        mobile: '',
        smsCode: '',
        userPassword: '',
        remember: true
      },
      buttonName: '发送验证码',
      isDisabled: false,
      time: 60,
      redirect: '',
      loginUrl: 'login'
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
        this.loginUrl = 'login'
      },
      immediate: true
    }
  },
  methods: {
    sendMsg() {
      const me = this
      this.$validator.validateAll('group-sms').then((result) => {
        if (result) {
          sendRegisterSms(this.registerForm).then(response => {
            me.isDisabled = true
            const interval = window.setInterval(function() {
              me.buttonName = '（' + me.time + ' 秒）'
              --me.time
              if (me.time < 0) {
                me.buttonName = '重新发送'
                me.time = 60
                me.isDisabled = false
                window.clearInterval(interval)
              }
            }, 1000)
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    handleRegister() {
      var that = this
      this.$validator.validateAll().then((result) => {
        if (result) {
          this.loading = true
          registerNormal(this.registerForm).then(() => {
            Dialog.alert({
              title: '恭喜',
              message: '您已注册成功！'
            }).then(() => {
              that.$router.push({ path: '/member' })
            })
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  }
}
</script>
