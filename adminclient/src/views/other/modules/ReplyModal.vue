<template>
  <a-modal :title="title" :width="600" :visible="visible" :confirmLoading="confirmLoading" @ok="handleOk" @cancel="handleCancel">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item label="留言ID">
          <a-input v-model="defaultId" disabled />
        </a-form-item>
        <a-form-item label="留言标题">
          <a-input v-model="replyTitle"/>
        </a-form-item>
        <a-form-item label="留言内容">
          <a-textarea :rows="4" v-model="content" />
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
  import {
    postCustomerService
  } from '@/api/user'

  export default {
    name: 'ReplyModal',
    data() {
      return {
        replyTitle: '',
        content: '',
        defaultId: '',
        title: '回复留言',
        labelCol: {
          xs: {
            span: 24
          },
          sm: {
            span: 5
          }
        },
        wrapperCol: {
          xs: {
            span: 24
          },
          sm: {
            span: 16
          }
        },
        visible: false,
        confirmLoading: false,
        mdl: {}
      }
    },
    beforeCreate() {
      this.form = this.$form.createForm(this)
      //console.log('form::', this.form)
    },
    created() {

    },
    methods: {
      add(id) {
        this.edit({
          parentId: id
        })
      },
      edit(record) {
        this.mdl = Object.assign({}, record)
        //console.log('内容======》', record);
        this.title = '回复' + record.playerName + '留言';
        this.defaultId = record.id;
        this.visible = true
        this.$nextTick(() => {
          this.form.setFieldsValue({ ...record
          })
        })
      },
      close() {
        this.$emit('close')
        this.content = '';
        this.defaultId = '';
        this.visible = false
      },
      handleOk() {
        const s = this
        if (s.defaultId && s.content && s.content.length > 3) {
          let queryParam = {
            id: s.defaultId,
            replyText: s.content,
            replyTitle: s.replyTitle
          }
          //console.log(queryParam);
          postCustomerService(queryParam).then(res => {
            //console.log(res);
            if (res) {
              s.$message.success(
                '留言成功',
                5,
              );
              s.close();
            }
          })
        }
      },
      handleCancel() {
        this.close()
      }

    }
  }
</script>
