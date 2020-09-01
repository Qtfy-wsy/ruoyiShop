<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="用户名" prop="username">
        <el-input
          v-model="queryParams.username"
          placeholder="请输入用户名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="身份证" prop="cardid">
        <el-input
          v-model="queryParams.cardid"
          placeholder="请输入身份证"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="手机号码" prop="mobile">
        <el-input
          v-model="queryParams.mobile"
          placeholder="请输入手机号码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="邮箱地址" prop="email">
        <el-input
          v-model="queryParams.email"
          placeholder="请输入邮箱地址"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择用户状态" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['member:UmsMember:add']"
        >新增</el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['member:UmsMember:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['member:UmsMember:export']"
        >导出</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="UmsMemberList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />

      <el-table-column label="手机号码" align="center" prop="mobile" />

      <el-table-column label="昵称" align="center" prop="nickname" />

            <el-table-column label="头像地址" width="80">
                  <template slot-scope="scope"><img :src="scope.row.image" width="50" height="50"></template>
                </el-table-column>
      <el-table-column label="性别" align="center" prop="gender"  :formatter="genderFormat"/>
      <el-table-column label="生日" align="center" prop="birthday" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.birthday, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>


      <el-table-column label="邮箱地址" align="center" prop="email" />

      <el-table-column label="总的消费金额" align="center" prop="consumptionAmount" />

      <el-table-column label="用户注册来源" align="center" prop="source" />
      <el-table-column label="用户状态" align="center" prop="status" />


      <el-table-column label="推荐人的会员" align="center" prop="recommended" />
      <el-table-column label="二级推荐人的会员 " align="center" prop="sRecommended" />
      <el-table-column label="会员自己的推荐码" align="center" prop="selfRecommendCode" />
      <el-table-column label="会员的佣金" align="center" prop="commission" />

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">

          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['member:UmsMember:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改会员对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">

         <el-form-item label="手机号码" prop="mobile">
                  <el-input v-model="form.mobile" placeholder="请输入手机号码" />
                </el-form-item>
        <el-form-item label="用户密码" prop="password">
          <el-input v-model="form.password" placeholder="请输入用户密码" />
        </el-form-item>
 <el-form-item label="头像地址">
               <el-upload
                 class="avatar-uploader"
                 style="width: auto; height: auto"
                 :action="uploadApi"
                 :show-file-list="false"
                 :on-success="uploadMobilePanicPicSuccess"
                 :headers="headers"
                 name="image"
                 accept=",.JPG,.jpeg,.png,.gif,.ico,.JPEG,.PNG,.GIF,.ICO,.jpg">
                 <img v-if="form.image" :src="form.image" style="max-height: 100px; display: block">
                 <i v-else class="el-icon-plus avatar-uploader-icon"></i>
               </el-upload>
             </el-form-item>

        <el-form-item label="真实姓名" prop="relename">
          <el-input v-model="form.relename" placeholder="请输入真实姓名" />
        </el-form-item>
        <el-form-item label="身份证" prop="cardid">
          <el-input v-model="form.cardid" placeholder="请输入身份证" />
        </el-form-item>
         <el-form-item label="推荐码" prop="recommondCode">
                  <el-input v-model="form.recommondCode" placeholder="请输入推荐码" />
                </el-form-item>

        <el-form-item label="性别" prop="gender">

           <el-select v-model="form.gender" placeholder="请选择性别  ">
                      <el-option
                        v-for="dict in genderStatusOptions"
                        :key="dict.dictValue"
                        :label="dict.dictLabel"
                        :value="dict.dictValue"
                      ></el-option>
                    </el-select>
        </el-form-item>
        <el-form-item label="生日" prop="birthday">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.birthday"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择生日">
          </el-date-picker>
        </el-form-item>


        <el-form-item label="邮箱地址" prop="email">
          <el-input v-model="form.email" placeholder="请输入邮箱地址" />
        </el-form-item>

        <el-form-item label="详细地址" prop="detailaddress">
          <el-input v-model="form.detailaddress" placeholder="请输入详细地址" />
        </el-form-item>
        <el-form-item label="兴趣爱好" prop="interest">
          <el-input v-model="form.interest" type="textarea" placeholder="请输入内容" />
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listUmsMember, getUmsMember, delUmsMember, addUmsMember, updateUmsMember, exportUmsMember,checkMobileExist,checkEmailExist,addstationLetters } from "@/api/member/UmsMember";
import {isMobilePhone, isEmail, isIdCardNo} from '@/utils/validate';
import {getToken} from '@/utils/auth'
export default {
  name: "UmsMember",
  data() {
   const mobileValidator = (rule, value, callback) => {
          if (!isMobilePhone(value)) {
            callback(new Error('请输入正确的手机号码'));
          } else {
            checkMobileExist(value).then(res => {
              if (res > 0) {
                callback(new Error('手机号码已经存在'));
              } else {
                callback();
              }
            })
          }
        }

        const emailValidator = (rule, value, callback) => {
          if (!value) {
            callback();
          } else {
            // 是否是邮箱
            if (!isEmail(value)) {
              callback(new Error('请输入正确的邮箱'));
            } else {
              checkEmailExist(value).then(res => {
                if (res > 0) {
                  callback(new Error('邮箱已经存在'));
                } else {
                  callback();
                }
              })
            }
          }
        }


        const cardValidator = (rule, value, callback) => {
          if (!value) {
            callback();
          } else {
            if (!isIdCardNo(value)) {
              callback(new Error('请输入正确的身份证号码'));
            } else {
              callback();
            }
          }
        }

        const passwordValidator = (rule, value, callback) => {
          if (value.length < 6) {
            callback(new Error('密码至少6个字符'));
          } else {
            callback();
          }
        }

        const againPasswordValidator = (rule, value, callback) => {
          if (value.length < 6) {
            callback(new Error('密码至少6个字符'));
          } else if (value != this.customerForm.password) {
            callback(new Error('两次输入的密码不一致'));
          } else {
            callback();
          }
        }
    return {
      // 遮罩层
      loading: true,
       headers: { // 设置上传的请求头部
                      Authorization: 'Bearer ' + getToken()
                    },
             uploadApi: process.env.VUE_APP_BASE_API+'/aliyun/oss/uploadToAliOss',// 上传文件路由
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      genderStatusOptions:[],
      // 会员表格数据
      UmsMemberList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 0,
        pageSize: 10,
        username: undefined,
        password: undefined,
        cardid: undefined,
        gender: undefined,
        mobile: undefined,
        email: undefined,
        status: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
       mobile: [
                   {
                     required: true,
                     trigger: 'blur',
                     validator: mobileValidator
                   }
                 ],
                 email: [
                   {
                     trigger: 'blur',
                     validator: emailValidator
                   }
                 ],
                 password: [
                   {
                     required: true,
                     trigger: 'blur',
                     validator: passwordValidator
                   }
                 ],
                 againpassword: [
                   {
                     required: true,
                     trigger: 'blur',
                     validator: againPasswordValidator
                   }
                 ],
                 cardId: [
                   {
                     trigger: 'blur',
                     validator: cardValidator
                   }
                 ],

      }
    };
  },
  created() {
    this.getList();
     this.getDicts("sys_user_sex").then(response => {
          this.genderStatusOptions = response.data;
        });
  },
  methods: {
    /** 查询会员列表 */
    getList() {
      this.loading = true;
      listUmsMember(this.queryParams).then(response => {
        this.UmsMemberList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
       uploadMobilePanicPicSuccess(res) {
                this.form.image = res;
              },
     // 1:待付款  字典翻译
        genderFormat(row, column) {
          return this.selectDictLabel(this.genderStatusOptions, row.status);
        },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        username: undefined,
        password: undefined,
        nickname: undefined,
        image: undefined,
        relename: undefined,
        cardid: undefined,
        gender: undefined,
        birthday: undefined,
        monthlyIncome: undefined,
        marriageStatus: "0",
        mobile: undefined,
        email: undefined,
        modifiedEmail: undefined,
        checkCode: undefined,
        province: undefined,
        city: undefined,
        county: undefined,
        detailaddress: undefined,
        interest: undefined,
        storeId: undefined,
        type: undefined,
        consumptionAmount: undefined,
        paypassword: undefined,
        source: undefined,
        status: "0",
        signNum: undefined,
        isMobileVerification: undefined,
        isEmailVerification: undefined,
        loginErrorCount: undefined,
        delFlag: undefined,
        createTime: undefined,
        modifyTime: undefined,
        delTime: undefined,
        lockTime: undefined,
        lastLoginTime: undefined,
        recommended: undefined,
        sRecommended: undefined,
        selfRecommendCode: undefined,
        commission: undefined,
        beloneStoreId: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 0;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加会员";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getUmsMember(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改会员";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateUmsMember(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }
            });
          } else {
            addUmsMember(this.form).then(response => {
              if (response.code === 200) {
                this.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除会员编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delUmsMember(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        }).catch(function() {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有会员数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportUmsMember(queryParams);
        }).then(response => {
          this.download(response.msg);
        }).catch(function() {});
    }
  }
};
</script>
