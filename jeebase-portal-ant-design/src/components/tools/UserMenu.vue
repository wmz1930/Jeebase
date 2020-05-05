<template>
  <div class="user-wrapper">
    <div class="content-box">
      <a href="https://pro.loacg.com/docs/getting-started" target="_blank">
        <span class="action">
          <a-icon type="question-circle-o"></a-icon>
        </span>
      </a>
      <notice-icon class="action"/>
      <a-dropdown>
        <span class="action ant-dropdown-link user-dropdown-menu">
          <a-avatar class="avatar" size="small" :src="avatar()"/>
          <span>{{ nickname() }}</span>
        </span>
        <a-menu slot="overlay" class="user-dropdown-menu-wrapper">
          <a-menu-item key="0">
            <router-link :to="{ name: 'center' }">
              <a-icon type="user"/>
              <span>个人中心</span>
            </router-link>
          </a-menu-item>
          <a-menu-item key="1">
            <router-link :to="{ name: 'settings' }">
              <a-icon type="setting"/>
              <span>账户设置</span>
            </router-link>
          </a-menu-item>
          <a-menu-item key="2" >
            <a href="javascript:;" @click="handleUpdatePwd">
              <a-icon type="setting"/>
              <span>修改密码</span>
            </a>
          </a-menu-item>
          <a-menu-divider/>
          <a-menu-item key="3">
            <a href="javascript:;" @click="handleLogout">
              <a-icon type="logout"/>
              <span>退出登录</span>
            </a>
          </a-menu-item>
        </a-menu>
      </a-dropdown>
    </div>

    <a-modal v-model="dialogFormVisible" :maskClosable="false" :destroyOnClose="true" title="修改密码">
      <a-form-model
        ref="changePwdForm"
        :model="changePwdForm"
        :rules="rules"
        :label-col="pwdLabelCol"
        :wrapper-col="pwdWrapperCol">
        <a-form-model-item label="旧密码" prop="oldPwd">
          <a-input v-model="changePwdForm.oldPwd" placeholder="输入旧密码" :max-length="32" type="password" />
        </a-form-model-item>
        <a-form-model-item label="新密码" prop="newPwd">
          <a-input v-model="changePwdForm.newPwd" placeholder="输入新密码" :max-length="32" type="password" />
        </a-form-model-item>
        <a-form-model-item label="新密码" prop="newPwdRe">
          <a-input v-model="changePwdForm.newPwdRe" placeholder="请再次输入新密码" :max-length="32" type="password" />
        </a-form-model-item>
      </a-form-model>
      <div slot="footer" class="dialog-footer">
        <a-button @click="dialogFormVisible = false">取消</a-button>
        <a-button type="primary" @click="updatePwdData">确定</a-button>
      </div>
    </a-modal>
  </div>
</template>

<script>
import NoticeIcon from '@/components/NoticeIcon'
import { mapActions, mapGetters } from 'vuex'
import { updatePwd } from '@/api/system/user'

export default {
  name: 'UserMenu',
  components: {
    NoticeIcon
  },
  data () {
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
      pwdLabelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      pwdWrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
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
      }
    }
  },
  methods: {
    ...mapActions(['Logout']),
    ...mapGetters(['nickname', 'avatar']),
    handleLogout () {
      const that = this

      this.$confirm({
        title: '提示',
        content: '真的要注销登录吗 ?',
        onOk () {
          return that.Logout({}).then(() => {
            window.location.reload()
          }).catch(err => {
            that.$message.error({
              title: '错误',
              description: err.message
            })
          })
        },
        onCancel () {
        }
      })
    },
    resetUpdatePwdForm () {
      this.changePwdForm = {
        oldPwd: '',
        newPwd: '',
        newPwdRe: ''
      }
    },
    handleUpdatePwd () {
      this.resetUpdatePwdForm()
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['changePwdForm'].clearValidate()
      })
    },
    updatePwdData () {
      this.$refs['changePwdForm'].validate(valid => {
        if (valid) {
          updatePwd(this.changePwdForm).then(() => {
            this.dialogFormVisible = false
            this.$message.success('更新成功')
            return this.Logout({}).then(() => {
              window.location.reload()
            }).catch(err => {
              this.$message.error({
                title: '错误',
                description: err.message
              })
            })
          })
        }
      })
    }
  }
}
</script>
