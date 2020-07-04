<template>
  <a-card :bordered="false" class="content">
    <div class="table-page-search-wrapper">
      <a-form-model layout="inline">
        <a-row :gutter="48">
          <a-col :md="6" :sm="24">
            <a-form-model-item label="接口名称">
              <a-input
                v-model="listQuery.methodName"
                placeholder="接口名称"
                class="filter-item"
                :maxLength="32"
                @keyup.enter.native="handleFilter" />
            </a-form-model-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-model-item label="操作人">
              <a-input
                v-model="listQuery.creator"
                placeholder="操作人"
                class="filter-item"
                :maxLength="32"
                @keyup.enter.native="handleFilter" />
            </a-form-model-item>
          </a-col>
          <a-col :md="6" :sm="24">
            <a-form-model-item label="日志类型">
              <a-select v-model="listQuery.logType" placeholder="日志类型" allow-clear >
                <a-select-option v-for="item in typeOption" :key="item.key" :value="item.key">
                  {{ item.label }}
                </a-select-option>
              </a-select>
            </a-form-model-item>
          </a-col>
          <template v-if="advanced">
            <a-col :md="6" :sm="24">
              <a-form-model-item label="">
              </a-form-model-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-model-item label="开始时间">
                <a-date-picker v-model.trim="listQuery.startTime" placeholder="开始时间" valueFormat="YYYY-MM-DD HH:mm:ss" style="width:100%;"/>
              </a-form-model-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-model-item label="结束时间">
                <a-date-picker v-model.trim="listQuery.endTime" placeholder="结束时间" valueFormat="YYYY-MM-DD HH:mm:ss" style="width:100%;"/>
              </a-form-model-item>
            </a-col>
          </template>
          <a-col :md="!advanced && 6 || 24" :sm="24">
            <span class="table-page-search-submitButtons" :style="advanced && { float: 'right', overflow: 'hidden' } || {} ">
              <a-button type="primary" @click="handleFilter">查询</a-button>
              <a-button style="margin-left: 8px" @click="() => listQuery = {}">重置</a-button>
              <a @click="toggleAdvanced" style="margin-left: 8px">
                {{ advanced ? '收起' : '展开' }}
                <a-icon :type="advanced ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form-model>
    </div>

    <div class="table-operator">
      <a-button type="primary" icon="cloud-download" @click="handleDownload" style="margin-left: 8px">导出</a-button>
    </div>

    <s-table
      ref="logTable"
      size="default"
      bordered
      :rowKey="row=>row.id"
      :columns="columns"
      :data="loadData"
      showPagination="auto"
      :pagination="logPagination"
    >
      <span slot="inParams" slot-scope="text, record">
        <span class="link-type" @click="handleInShow(record.inParams)">{{ record.inParams }}</span>
      </span>
      <span slot="outParams" slot-scope="text, record">
        <span class="link-type" @click="handleOutShow(record.outParams)">{{ record.outParams }}</span>
      </span>
      <span slot="logType" slot-scope="text, record">
        <span>{{ record.logType | logTypeFilter }}</span>
      </span>
      <span slot="createTime" slot-scope="text, record">
        <span>{{ record.createTime | moment }}</span>
      </span>
    </s-table>

    <a-modal :visible.sync="dialogFormVisible" :title="textMap[dialogStatus]">
      <div class="editor-container">
        <json-editor ref="jsonEditor" v-model="jsonValue" />
      </div>
      <div slot="footer" class="dialog-footer">
        <a-button @click="dialogFormVisible = false">关闭</a-button>
      </div>
    </a-modal>
  </a-card>
</template>

<script>
import { STable } from '@/components'
import { fetchList } from '@/api/system/common/log'
import moment from 'moment'
import JsonEditor from '@/components/JsonEditor'

export default {
  name: 'LogTable',
  components: { STable, JsonEditor },
  filters: {
    logTypeFilter (logType) {
      const logTypeMap = {
        1: '操作日志'
      }
      return logTypeMap[logType]
    }
  },
  data () {
    return {
      logLabelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      logWrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      advanced: false,
      tableKey: 0,
      list: [],
      total: 0,
      listLoading: true,
      listQuery: {
        methodName: '',
        logType: '',
        operationName: '',
        startTime: '',
        endTime: ''
      },
      typeOption: [
        { label: '操作日志', key: '1' }
      ],
      dialogFormVisible: false,
      textMap: {
        outParams: '出参详情',
        inParams: '入参详情'
      },
      dialogStatus: '',
      downloadLoading: false,
      jsonValue: '',
      // 表头
      columns: [
        {
          title: '序号',
          align: 'center',
          dataIndex: 'id'
        },
        {
          title: '接口名称',
          align: 'center',
          dataIndex: 'methodName'
        },
        {
          title: '入参',
          align: 'center',
          ellipsis: true,
          dataIndex: 'inParams',
          scopedSlots: { customRender: 'inParams' }
        },
        {
          title: '出参',
          align: 'center',
          ellipsis: true,
          dataIndex: 'outParams',
          scopedSlots: { customRender: 'outParams' }
        },
        {
          title: '日志类型',
          align: 'center',
          dataIndex: 'logType',
          scopedSlots: { customRender: 'logType' }
        },
        {
          title: '操作名称',
          align: 'center',
          dataIndex: 'operationName'
        },
        {
          title: '操作的IP',
          align: 'center',
          dataIndex: 'operationIp'
        },
        {
          title: '记录时间',
          align: 'center',
          dataIndex: 'createTime',
          scopedSlots: { customRender: 'createTime' }
        },
        {
          title: '操作人',
          align: 'center',
          dataIndex: 'creator'
        }
      ],
      logPagination: {
        defaultPageSize: 10,
        showQuickJumper: true,
        defaultCurrent: 1,
        showTotal: (total, range) => `共 ${total} 条`
      },
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        return fetchList(Object.assign(parameter, this.listQuery))
          .then(res => {
            this.list = res.data
            return res
          })
      }
    }
  },
  created () {
    this.getList()
  },
  methods: {
    toggleAdvanced () {
      this.advanced = !this.advanced
    },
    getList () {
      this.listLoading = true
      fetchList(this.listQuery).then(response => {
        this.list = response.data
        this.total = response.count
        this.listLoading = false
      })
    },
    handleFilter () {
      this.listQuery.current = 1
      this.getList()
    },
    handleInShow (rowJson) {
      this.jsonValue = ''
      this.dialogStatus = 'inParams'
      this.jsonValue = JSON.parse(rowJson)
      this.dialogFormVisible = true
    },
    handleOutShow (rowJson) {
      this.jsonValue = ''
      this.dialogStatus = 'outParams'
      this.jsonValue = JSON.parse(rowJson)
      this.dialogFormVisible = true
    },
    handleDownload () {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = [
          '序号',
          '接口名称',
          '入参',
          '出参',
          '日志类型',
          '操作名称',
          '操作ID',
          '记录时间',
          '操作人'
        ]
        const filterVal = [
          'id',
          'methodName',
          'inParams',
          'outParams',
          'logType',
          'operationName',
          'operationIp',
          'createTime',
          'creator'
        ]
        const data = this.formatJson(filterVal, this.list)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: '操作日志列表'
        })
        this.downloadLoading = false
      })
    },
    formatJson (filterVal, jsonData) {
      return jsonData.map(v =>
        filterVal.map(j => {
          if (j === 'createTime') {
            return moment(v[j])
          } else if (j === 'logType') {
            return this.$options.filters['logTypeFilter'](v[j])
          } else {
            return v[j]
          }
        })
      )
    }
  }
}
</script>
<style lang="less" scoped>
.link-type, .link-type:focus {
    color: #337ab7;
    cursor: pointer;
}
</style>
