<template>
  <div class="app-container">
    <el-form label-position="right" :model="form" :rules="rules" ref="form" label-width="100px"
             style="margin-top: 20px">
      <el-form-item prop="name">
        <span slot="label">帮助标题</span>
        <el-input v-model="form.name" maxlength="25"/>
      </el-form-item>
      <el-form-item prop="helpCateId">
        <span slot="label">所属分类</span>
        <el-select v-model="form.helpCateId" filterable placeholder="请选择分类">
          <el-option
            v-for="helpCate in helpCateList"
            :key="helpCate.id"
            :label="helpCate.name"
            :value="helpCate.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="sort">
        <span slot="label">排序</span>
        <el-input-number v-model="form.sort" controls-position="right" :min="1" :max="99"></el-input-number>
      </el-form-item>
      <el-form-item>
        <span slot="label"><span class="labelImportant">*</span>显示</span>
        <el-switch
          style="vertical-align: sub"
          v-model="form.isShow"
          active-color="#13ce66"
          inactive-color="#ff4949"
          :active-value="0"
          :inactive-value="1">
        </el-switch>
      </el-form-item>
      <el-form-item label="帮助内容">
        <tinymce v-model="form.desc" :height="500"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="updateHelp">保存</el-button>
        <router-link to="/smsManager/helpManager/CmsHelp">
          <el-button type="info">取消</el-button>
        </router-link>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import Tinymce from '@/components/Tinymce';
  import {queryAllHelpCategoryForUpdate, queryHelpById, updateHelp} from '@/api/cms/helplist';

  export default {
    components: {Tinymce},
    data() {
      return {
        isProcess: false, // 接口是否处理中
        helpCateList: null, // 帮助分类集合
        form: { // 新增form表单
          name: '', // 帮助标题
          helpCateId: '', // 帮助分类id
          sort: '', // 帮助排序
          isShow: 0, // 是否显示 0 显示 1 不显示
          desc: '', // 帮助内容
        },
        rules: { // 校验规则
          name: [
            {
              required: true,
              message: '请输入帮助标题',
              trigger: 'blur'
            }
          ],
          helpCateId: [
            {
              required: true,
              message: '请选择帮助分类',
              trigger: 'blur'
            }
          ],
          sort: [
            {
              required: true,
              message: '请输入排序',
              trigger: 'blur'
            }
          ],
        },
      }
    },
    created() {
      this.queryHelpById(this.$route.query.id);
    },
    methods: {
      // 查询根据id查询帮助信息
      queryHelpById(id) {
        queryAllHelpCategoryForUpdate().then(res => {
          this.helpCateList = res;
        });
        queryHelpById(id).then(res => {
          if (res.flag) {
            this.form = res.data;
          }
        });
      },
      // 修改帮助
      updateHelp() {
        this.$refs['form'].validate(valid => {
          if (valid && this.validateLength() && !this.isProcess) {
            this.isProcess = true;
            updateHelp(this.form).then(res => {
              this.isProcess = false;
              if (res == 1) {
                this.$message({
                  type: 'success',
                  message: '修改成功!'
                });
                this.$router.push({path: '/smsManager/helpManager/CmsHelp'})
              } else {
                this.$message({
                  message: '修改失败',
                  type: 'error'
                })
              }
            })
          }
        });
      },
      // 验证帮助内容长度
      validateLength() {
        if (this.form.desc.length > 65535) {
          this.$message({
            message: '帮助内容长度不能超过65535',
            type: 'warning'
          });
          return false;
        }
        return true;
      },
    }
  }
</script>
