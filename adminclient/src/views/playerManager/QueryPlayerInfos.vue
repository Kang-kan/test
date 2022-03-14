<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="0">
          <a-col :md="5" :sm="12">
            <a-row>
              <a-col :md="7" style="line-height: 32px;">区服:</a-col>
              <a-col :md="14">
                <a-select size="default" @change="serverChange">
                  <a-select-option
                    v-for="i in serverList"
                    :key="i.id"
                    :value="i.id"
                  >{{i.name}}({{i.id}})</a-select-option>
                </a-select>
              </a-col>
            </a-row>
          </a-col>
          <a-col :md="5" :sm="24" style="padding-right: 18px;">
            <a-input placeholder="请输入账号id" v-model="queryParam.accountId" />
          </a-col>
          <a-col :md="5" :sm="24" style="padding-right: 18px;">
            <a-row>
              <a-input placeholder="请输入玩家id" v-model="queryParam.playerId" />
            </a-row>
          </a-col>
          <a-col :md="5" :sm="24" style="padding-right: 18px;">
            <a-input placeholder="请输入玩家名称" v-model="queryParam.playerName" />
          </a-col>
          <a-col :md="2" :sm="24">
            <span class="table-page-search-submitButtons">
              <a-button type="primary" style="margin-left: 8px" @click="seach">查询</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>

    <a-divider style="margin-bottom: 32px" />
    <detail-list title="账号信息" class="infos">
      <detail-list-item term="玩家id">{{ this.playerInfo.id }}</detail-list-item>
      <detail-list-item term="玩家名">{{ this.playerInfo.playerName }}</detail-list-item>
      <detail-list-item term="服务器">{{ this.playerInfo.serverId | serverName }}</detail-list-item>
      <detail-list-item term="账号">{{ this.playerInfo.account}}</detail-list-item>
      <detail-list-item term="渠道">{{ this.playerInfo.channelId }}</detail-list-item>
      <detail-list-item term="是否在线">{{ this.playerInfo.online | onlineText }}</detail-list-item>
      <detail-list-item term="上次登录ip">{{ this.playerInfo.ip }}</detail-list-item>
      <detail-list-item term="注册时间">{{ this.playerInfo.registTime }}</detail-list-item>
      <detail-list-item term="上次登录时间">{{ this.playerInfo.lastLoginTime }}</detail-list-item>
    </detail-list>

    <a-divider style="margin-bottom: 32px" />
    <detail-list title="角色信息" class="infos">
      <detail-list-item term="等级">{{ this.playerInfo.level }}</detail-list-item>
      <detail-list-item term="vip等级">{{ this.playerInfo.vipLevel }}</detail-list-item>
      <detail-list-item term="封号到期时间">{{ this.playerInfo.banPlayerTime | blockTimeText }}</detail-list-item>
      <detail-list-item term="禁言到期时间">{{ this.playerInfo.banWordTime | blockTimeText }}</detail-list-item>
      <detail-list-item term="仙晶">{{ this.playerInfo.gold }}</detail-list-item>
      <detail-list-item term="金币">{{ this.playerInfo.money }}</detail-list-item>
      <detail-list-item term="内币">{{ this.playerInfo.fakeGold }}</detail-list-item>
      <detail-list-item term="战斗力">{{ this.playerInfo.power }}</detail-list-item>
      <detail-list-item term="总充值金额">{{ this.playerInfo.totalCharge }}</detail-list-item>
    </detail-list>
  </a-card>
</template>

<script>
import { formatTimestamp } from '@/utils/util'
import moment from 'moment'
import { getQueryPlayerInfos } from '@/api/user'
import { PageView } from '@/layouts'
import DetailList from '@/components/tools/DetailList'
const DetailListItem = DetailList.Item

let that

export default {
  name: 'QueryPlayerInfos',
  components: {
    PageView,
    DetailList,
    DetailListItem
  },
  data() {
    return {
      channelIdList: [],
      serverList: [],
      playerInfo: {},
      // 查询参数
      queryParam: {
        serverId: '',
        accountId: '',
        playerId: '',
        playerName: ''
      }
    }
  },
  beforeCreate() {
    that = this
  },
  created() {
    
  },
  methods: {
    seach() {
      getQueryPlayerInfos(this.queryParam).then(res => {
        if (!res || res.length == 0) {
          return
        }
        let playerInfo = res[0]
        playerInfo.account = playerInfo.account.split('.')[0]
        playerInfo.registTime = formatTimestamp(playerInfo.registTime)
        playerInfo.lastLoginTime = formatTimestamp(playerInfo.lastLoginTime)

        let z = Math.floor(playerInfo.level / 1000)
        let j = Math.floor(playerInfo.level % 1000)
        playerInfo.level = z > 0 ? z + '转' + j + '级' : j + '级'

        playerInfo.totalCharge = playerInfo.totalCharge > 0 ? playerInfo.totalCharge / 100 + '元' : '0元'

        this.playerInfo = playerInfo
      })
    },
    serverChange(value) {
      this.queryParam.serverId = Number(value)
    }
  },
  mounted: function() {
    let s = this
    s.$ajax.get('../gameVar.json').then(res => {
      if (!res || !res.data) {
        return
      }
      this.channelIdList = res.data.channelIdList
    })
    this.$http.get('/roleGameServer/getMyServers').then(res => {
      this.serverList = res
      if (!res || res.length == 0) {
        return
      }
    })
  },
  filters: {
    serverName(serverId) {
      let name = ''
      if (serverId == undefined) {
        return name
      }
      that.serverList.forEach(el => {
        if (el.id == serverId) {
          name = el.name + '(' + el.id + ')'
        }
      })
      return name
    },
    onlineText(status) {
      const statusMap = {
        true: '在线',
        false: '不在线'
      }
      return statusMap[status]
    },
    blockTimeText(time) {
      let text = ''
      if (time == undefined) {
        return text
      } else if (time == 0) {
        return text
      } else if (time <= 0) {
        return '永久'
      }
      formatTimestamp(time)
    }
  }
}
</script>

<style lang="less" scoped>
.infos {
  color: rgba(0, 0, 0, 0.85);
  font-size: 16px;
  font-weight: 500;
  margin-bottom: 16px;
}
</style>
