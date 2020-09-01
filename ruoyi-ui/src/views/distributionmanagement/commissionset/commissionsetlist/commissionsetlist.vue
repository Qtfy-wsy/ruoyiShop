<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input placeholder="商品编号" size="medium" v-model="listQuery.id" maxlength="20" style="width: 150px;"
                class="filter-item"/>
      <el-input placeholder="商品标题" size="medium" v-model="listQuery.name" maxlength="20" style="width: 150px;"
                class="filter-item"/>
      <el-select filterable size="medium" v-model="listQuery.brandId" placeholder="请选择品牌"
                 style="margin-bottom: 10px; vertical-align: top">
        <el-option
          v-for="item in brandsOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>

      <el-cascader
        style="margin-bottom: 10px; vertical-align: top"
        size="medium"
        placeholder="请选择分类"
        :props="props"
        v-model="cateSelectedOptions">
      </el-cascader>

      <!--:options="cateOptions"-->


      <el-button class="filter-item" type="primary" size="medium" icon="el-icon-search" @click="handleQuery">搜索
      </el-button>
      <br/>
      <el-button class="filter-item" size="medium" type="primary" icon="el-icon-setting"
                 @click="prepareToBatchUpdate()">
        批量佣金设置
      </el-button>
    </div>

    <el-table
      :data="list"
      v-loading="listLoading"
      border
      fit
      highlight-current-row
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" align="center" width="40"/>
      <el-table-column label="编号" min-width="200">
              <template slot-scope="scope">{{ scope.row.id }}</template>
            </el-table-column>
      <el-table-column label="商品图片" width="80">
        <template slot-scope="scope"><img :src="scope.row.url" width="50" height="50" alt=""></template>
      </el-table-column>
      <el-table-column label="商品标题" min-width="200">
        <template slot-scope="scope">{{ scope.row.name }}</template>
      </el-table-column>
      <el-table-column label="销售价(元)">
        <template slot-scope="scope">{{ scope.row.price |toFixed }}</template>
      </el-table-column>
      <el-table-column label="分类">
        <template slot-scope="scope">{{ scope.row.thirdCategory.name }}</template>
      </el-table-column>
      <el-table-column label="品牌">
        <template slot-scope="scope">{{ scope.row.brand.name }}</template>
      </el-table-column>
      <el-table-column label="一级佣金比例" width="110">
        <template slot-scope="scope">{{ scope.row.commissionRate | toFixed}}</template>
      </el-table-column>
      <el-table-column label="二级佣金比例" width="110">
        <template slot-scope="scope">{{ scope.row.sCommissionRate | toFixed}}</template>
      </el-table-column>
      <el-table-column label="操作" align="left" width="100">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="showDialogVisible(scope.row.id)">佣金设置</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="pagination-container">
      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :current-page="listQuery.pageNum+1" :page-sizes="[10,20,30,50]" :page-size="listQuery.pageSize"
                     layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>

    <el-dialog title="佣金设置" :visible.sync="setDialogVisible">
      <el-form label-position="right" :model="form" :rules="rules" ref="form" label-width="120px">
        <el-form-item>
          <span slot="label"><span class="labelImportant">*</span>一级佣金比例</span>
          <input type="hidden" v-model="form.settingId"/>
          <input type="hidden" v-model="updateType"/>
          <el-form-item prop="commissionRate">
            <el-tooltip class="item" effect="dark" content="请输入0-100之间的整数" placement="right">
              <el-input type="number" maxlength="3" v-model="form.commissionRate">
                <template slot="append">%</template>
              </el-input>
            </el-tooltip>
          </el-form-item>
        </el-form-item>
        <el-form-item prop="sCommissionRate">
          <span slot="label">二级佣金比例</span>
          <el-tooltip class="item" effect="dark" content="请输入0-100之间的整数" placement="right">
            <el-input type="number" maxlength="3" v-model="form.sCommissionRate">
              <template slot="append">%</template>
            </el-input>
          </el-tooltip>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="setDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="setData">确定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>

  import {
    commissionSettingList,
    queryBrand,
    querycategorybyparentid,
    updatecommission,
    updatecommissions,
    queryspusforcommissionbyid
  } from '@/api/commissionsetlist';

  import {
    zeroToHundred
  } from '@/utils/validate';

  export default {
    data() {
      return {
        isProcess: false, //接口是否正在处理中
        setDialogVisible: false, //设置窗口 (false 隐藏) , (true 展示)
        listLoading: true, //加载中
        settingId: '', //当前正在更新的记录 ID
        commissionRate: '', //金额
        listQuery: {
          pageNum: 0,
          pageSize: 10,
          id: '',
          name: '',
          brandId: 0,
          firstCateId: 0,
          secondCateId: 0,
          thirdCateId: 0
        }, // 查询参数
        list: null, // 订单数据,
        total: null, // 数据总数,
        brandsOptions: [{
          value: 0,
          label: '请选择品牌'
        }],//品牌数组,
        cateOptions: [{
          value: '',
          label: '全部',
        }], // 分类数组
        cateSelectedOptions: [], //分类数组 id
        selectedIds: [],// 选中的id
        updateType: '',//更新操作类型  0 单个更新  批量更新
        form: { // 提交form表单
          settingId: '', // 商品id
          commissionRate: '', // 佣金比例
          sCommissionRate: '', // 二级佣金比例
        },
        props: {
          lazy: true,
          lazyLoad(node, resolve) {
            const {level} = node;
            let parentId;
            let categoryList = [];
            if (node.value) {
              parentId = node.value;
            } else {
              parentId = 0;
              let all = {
                id: 0,
                name: '全部'
              };
              categoryList.push(all)
            }
            querycategorybyparentid(parentId).then(response => {
              categoryList = categoryList.concat(response);
              const nodes = Array.from(categoryList)
                .map(item => ({
                  value: item.id,
                  label: item.name,
                  leaf: (item.id == 0) ? (level >= 0) : (level >= 2)
                }));
              // 通过调用resolve将子节点数据返回，通知组件数据加载完成
              resolve(nodes);
            });
          }
        },
        rules: { //校验规则
          commissionRate: [
            {
              required: true,
              trigger: 'blur',
              validator: zeroToHundred,
            }
          ],
          sCommissionRate: [
            {
              required: true,
              trigger: 'blur',
              validator: zeroToHundred,
            }
          ]
        }
      }
    },
    created() {
      this.getList();
      this.queryBrand();
    },
    filters: {
      toFixed(value) {
        // 截取当前数据到小数点后两位
        let realVal = Number(value).toFixed(2);
        if (realVal == 0.00) {
          return '';
        }
        return realVal;
      },
    },
    methods: {
      //准备批量修改
      prepareToBatchUpdate() {
        this.updateType = 1;
        if (this.selectedIds && this.selectedIds.length > 0) {
          this.form.commissionRate = '';
          this.form.sCommissionRate = '';
          this.setDialogVisible = true;
        } else {
          this.$message({
            message: '请至少选择一条记录！',
            type: 'warning'
          })
        }
      },
      //准备修改
      showDialogVisible(id) {
        this.form.settingId = id;
        this.updateType = 0;
        queryspusforcommissionbyid(id).then(response => {
          this.form.commissionRate = response.commissionRate * 100;
          this.form.sCommissionRate = response.sCommissionRate * 100;
        });
        this.setDialogVisible = true;
      },
      //加载列表
      getList() {
        this.listLoading = true
        commissionSettingList(this.listQuery).then(response => {
          this.list = response.data;
          this.total = response.recordsTotal;
          this.listLoading = false
        });
      },
      //查询品牌信息
      queryBrand() {
        queryBrand().then(response => {
          for (let i = 0; i < response.length; i++) {
            let brand = {
              value: response[i].id,
              label: response[i].name
            }
            this.brandsOptions.push(brand);
          }
        });
      },
      handleCurrentChange(val) {
        this.listQuery.pageNum = val - 1;
        this.getList();
      },
      handleSizeChange(val) {
        this.listQuery.pageNum = 0;
        this.listQuery.pageSize = val;
        this.getList();
      },
      handleSelectionChange(val) {
        if (val && val.length > 0) {
          this.selectedIds = val.map(row => row.id);
        } else {
          this.selectedIds = [];
        }
      },

      // 过滤查询
      handleQuery() {
        if (this.cateSelectedOptions.length == 3) {
          this.listQuery.firstCateId = this.cateSelectedOptions[0];
          this.listQuery.secondCateId = this.cateSelectedOptions[1];
          this.listQuery.thirdCateId = this.cateSelectedOptions[2];
        } else {
          this.listQuery.firstCateId = 0;
          this.listQuery.secondCateId = 0;
          this.listQuery.thirdCateId = 0;
        }
        this.listQuery.pageNum = 0;
        this.getList();
      },


      //更新一条记录
      updateOne(param) {
        if (this.isProcess) {
          return;
        }
        this.isProcess = true;
        updatecommission(param).then(response => {
          this.isProcess = false;
          if (response > 0) {
            this.getList();
            this.$message({
              type: 'success',
              message: '佣金设置成功'
            });
          } else {
            this.updateError();
          }
        })
      },
      //批量更新
      updateBatch(param) {
        if (this.isProcess) {
          return;
        }
        this.isProcess = true;
        updatecommissions(param).then(response => {
          this.isProcess = false;
          if (response == 1) {
            this.getList();
            this.$message({
              type: 'success',
              message: '佣金设置成功'
            });
          } else {
            this.updateError();
          }
        })
      },
      //操作失败提示
      updateError() {
        this.$message({
          type: 'error',
          message: '批量修改失败'
        })
      },
      //设置佣金比例
      setData() {
        this.$refs['form'].validate(valid => {
          if (valid) {
            if (!!this.form.sCommissionRate && this.form.sCommissionRate > 0 && (!this.form.commissionRate || this.form.commissionRate == '0')) {
              this.$message({
                type: 'error',
                message: '如果填写了二级佣金比例，则一级佣金比例必须不能为零！'
              });
              return;
            }
            this.setDialogVisible = false
            if (this.updateType == 0) {
              let param = {
                id: this.form.settingId,
                commissionRate: this.form.commissionRate / 100,
                sCommissionRate: this.form.sCommissionRate / 100
              };
              this.updateOne(param);
            } else {
              let param = {
                ids: this.selectedIds.toString(),
                commissionRate: this.form.commissionRate / 100,
                sCommissionRate: this.form.sCommissionRate / 100
              }
              this.updateBatch(param);
            }
          }
        });
      }
    }
  }
</script>
