<template>
  <div class="app-container">
    <el-form label-position="right" :model="form" :rules="rules" ref="form" label-width="100px"
             style="margin-top: 20px">
      <el-form-item prop="title">
        <span slot="label">文章标题</span>
        <el-input v-model="form.title" maxlength="128"/>
      </el-form-item>
      <el-form-item label="作者">
        <el-input v-model="form.author" maxlength="45"/>
      </el-form-item>
      <el-form-item prop="columnId">
        <span slot="label">所属栏目</span>
        <el-select v-model="form.columnId" filterable placeholder="请选择栏目">
          <el-option
            v-for="column in columnlist"
            :key="column.id"
            :label="column.name"
            :value="column.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="sort">
        <span slot="label">排序</span>
        <el-input-number v-model="form.sort" controls-position="right" :min="1" :max="99"></el-input-number>
      </el-form-item>
      <el-form-item>
        <span slot="label"><span class="labelImportant">*</span>发布</span>
        <el-switch
          style="vertical-align: sub"
          v-model="form.isRelease"
          active-color="#13ce66"
          inactive-color="#ff4949"
          active-value="0"
          inactive-value="1">
        </el-switch>
      </el-form-item>
      <!--<el-form-item label="SEO关键字">-->
        <!--<el-input v-model="form.seoKeywords" maxlength="128"/>-->
      <!--</el-form-item>-->
      <!--<el-form-item label="SEO描述">-->
        <!--<el-input v-model="form.seoDesc" maxlength="256"/>-->
      <!--</el-form-item>-->
      <el-form-item label="文章内容">
        <tinymce v-model="form.desc" :height="500"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="updateArticle">保存</el-button>
        <router-link to="/smsManager/articleMamaber/CmsArticle">
          <el-button type="info">取消</el-button>
        </router-link>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import Tinymce from '@/components/Tinymce';
  import {queryChildColumnListForArticleForUpdate, queryArticleById, updateArticle} from '@/api/cms/article';

  export default {
    components: {Tinymce},
    data() {
      return {
        isProcess: false, // 接口是否处理中
        columnlist: null, // 栏目集合
        form: { // 新增form表单
          id: null, // 文章id
          title: '', // 文章标题
          author: '', // 作者
          columnId: '', // 所属栏目
          sort: null, // 排序
          isRelease: '0', // 是否发布 0 是 1 否
          seoKeywords: '', // SEO关键字
          seoDesc: '', // SEO描述
          desc: '', // 文章内容
        },
        rules: { // 校验规则
          title: [
            {
              required: true,
              message: '请输入文章标题',
              trigger: 'blur'
            }
          ],
          columnId: [
            {
              required: true,
              message: '请选择所属栏目',
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
      this.queryArticleById(this.$route.query.id);
    },
    methods: {
      // 根据id查询文章信息
      queryArticleById(id) {
        queryChildColumnListForArticleForUpdate().then(res => {
          this.columnlist = res;
        });
        queryArticleById(id).then(res => {
          if (res.flag) {
            this.form = res.data;
            this.form.id = id;
          }
        });
      },
      // 修改文章
      updateArticle() {
        this.$refs['form'].validate(valid => {
          if (valid && this.validateLength() && !this.isProcess) {
            this.isProcess = true;
            updateArticle(this.form).then(res => {
              this.isProcess = false;
              if (res == -1) {
                this.$message({
                  message: '文章实体不能为空!',
                  type: 'error'
                })
              } else if (res == 1) {
                this.$message({
                  type: 'success',
                  message: '修改成功!'
                });
                this.$router.push({path: '/smsManager/articleMamaber/CmsArticle'})
              } else {
                this.$message({
                  message: '未知错误',
                  type: 'error'
                })
              }
            })
          }
        });
      },
      // 验证文章内容长度
      validateLength() {
        if (this.form.desc.length > 65535) {
          this.$message({
            message: '文章内容长度不能超过65535',
            type: 'warning'
          });
          return false;
        }
        return true;
      },
    }
  }
</script>
