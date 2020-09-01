<template>
  <div class="app-container">
    <el-alert title="注意！ 商品默认发全国，如果对应的地区没有设置运费，那么此地区将会使用默认运费。" type="warning" :closable="false" show-icon/>
    <el-form :model="form" :rules="logisticsRules" ref="logisticsform" label-position="right" label-width="150px"
             style="margin-top: 20px">
      <el-form-item prop="name">
        <span slot="label">模板名称</span>
        <el-input v-model="form.name" maxlength="7"/>
      </el-form-item>
      <el-form-item>
        <span slot="label"><span class="labelImportant">*</span>运费承担</span>
        <el-select v-model="form.freightBear" class="filter-item" size="medium" placeholder="请选择运费承担方">
          <el-option label="买家承担运费" value="0"/>
          <el-option label="卖家承担运费" value="1"/>
        </el-select>
      </el-form-item>
      <el-form-item v-if="form.freightBear=='0'">
        <span slot="label"><span class="labelImportant">*</span>计价方式</span>
        <el-select v-model="form.pricintMethod" class="filter-item" size="medium" placeholder="请选择计价方式">
          <el-option label="按件计价" value="0"/>
          <el-option label="按重计价" value="1"/>
        </el-select>
      </el-form-item>
      <el-form-item v-if="form.freightBear=='0'">
        <span slot="label"><span class="labelImportant">*</span>默认运费</span>
        <el-form-item prop="shippingMethods[0].first" style="width: 170px; display: inline-block">
          <el-input v-model="form.shippingMethods[0].first" maxlength="10" style="width: 130px; margin-right: 5px">
            <template slot="append">{{form.pricintMethod =='0'?'件':'g'}}</template>
          </el-input>
          内，
        </el-form-item>
        <el-form-item prop="shippingMethods[0].money" style="width: 200px; display: inline-block">
          <el-input v-model="form.shippingMethods[0].money" maxlength="10" style="width: 130px; margin-right: 5px">
            <template slot="append">元</template>
          </el-input>
          ，每增加
        </el-form-item>
        <el-form-item prop="shippingMethods[0].firstPlu" style="width: 220px; display: inline-block">
          <el-input v-model="form.shippingMethods[0].firstPlu" tmaxlength="10" style="width: 130px; margin:0 5px">
            <template slot="append">{{form.pricintMethod =='0'?'件':'g'}}</template>
          </el-input>
          ，增加运费
        </el-form-item>
        <el-form-item prop="shippingMethods[0].moenyPlu" style="width: 140px; display: inline-block">
          <el-input v-model="form.shippingMethods[0].moenyPlu" maxlength="10" style="width: 130px; margin:0 5px">
            <template slot="append">元</template>
          </el-input>
        </el-form-item>
      </el-form-item>
      <el-form-item v-if="form.freightBear=='0'">
        <span slot="label">收货地区</span>
        <el-button type="primary" size="small" icon="el-icon-plus" @click="toAddArea()">添加收货地区</el-button>
        <el-table
          style="margin-top: 10px"
          :data="shippingMethods"
          border
          fit
          highlight-current-row
        >
          <el-table-column label="收货地区" min-width="400">
            <template slot-scope="scope">{{ scope.row.areaNames }}</template>
          </el-table-column>
          <el-table-column :label="form.pricintMethod =='0'?'首件':'首重(g)'">
            <template slot-scope="scope">{{ scope.row.first }}</template>
          </el-table-column>
          <el-table-column label="首费(元)">
            <template slot-scope="scope">{{ scope.row.money }}</template>
          </el-table-column>
          <el-table-column :label="form.pricintMethod =='0'?'续件':'续重(g)'">
            <template slot-scope="scope">{{ scope.row.firstPlu }}</template>
          </el-table-column>
          <el-table-column label="续费(元)">
            <template slot-scope="scope">{{ scope.row.moenyPlu }}</template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button size="mini" style="margin: 0 10px 0 0" type="danger" @click="deleteArea(scope.row)">删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-form-item>

      <el-form-item v-if="form.freightBear=='0'">
        <span slot="label">包邮条件</span>
        <el-button type="primary" size="small" icon="el-icon-plus" @click="toAddFreeShip">添加包邮条件</el-button>
        <el-table
          style="margin-top: 10px"
          :data="shippingMethodFreeShips"
          border
          fit
          highlight-current-row
        >
          <el-table-column label="收货地区" min-width="400">
            <template slot-scope="scope">{{ scope.row.areaNames }}</template>
          </el-table-column>
          <el-table-column label="运送方式">快递配送</el-table-column>
          <el-table-column label="包邮条件" min-width="150">
            <template slot-scope="scope">
              <span v-if="scope.row.type== 0">件数满{{scope.row.num}}件 包邮</span>
              <span v-if="scope.row.type== 1">金额满{{scope.row.money}}元 包邮</span>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button size="mini" style="margin: 0 10px 0 0" type="danger" @click="deleteFreeShip(scope.row)">删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-form-item>
      <el-form-item>
        <router-link to="/order/delevierManager/OmsLogisticsTemplate">
          <el-button type="info" icon="el-icon-back">返回模板列表</el-button>
        </router-link>
        <el-button type="primary" @click="saveLogistics()">保存</el-button>
      </el-form-item>
    </el-form>

    <el-dialog title="添加收货地区" :visible.sync="dialogFormVisible">
      <el-form :model="shippingMethod" :rules="rules" ref="shippingMethodform" label-position="right"
               label-width="100px">
        <el-form-item>
          <span slot="label"><span class="labelImportant">*</span>收货地区</span>
          <el-tree
            style="max-height: 300px; overflow: auto; padding: 10px 0; border: 1px solid #DCDFE6; border-radius: 4px"
            ref="tree"
            :data="provinces"
            show-checkbox
            default-expand-all
            node-key="id"
            :props="defaultProps"
          />
        </el-form-item>
        <el-form-item prop="first">
          <span slot="label">{{form.pricintMethod =='0'?'首件个数':'首重'}}</span>
          <el-input v-model="shippingMethod.first" maxlength="10"/>
        </el-form-item>
        <el-form-item prop="money">
          <span slot="label">{{form.pricintMethod =='0'?'首件运费':'首重运费'}}</span>
          <el-input v-model="shippingMethod.money" maxlength="10"/>
        </el-form-item>
        <el-form-item prop="firstPlu">
          <span slot="label">{{form.pricintMethod =='0'?'续件个数':'续重'}}</span>
          <el-input v-model="shippingMethod.firstPlu" maxlength="10"/>
        </el-form-item>
        <el-form-item prop="moenyPlu">
          <span slot="label">{{form.pricintMethod =='0'?'续件运费':'续重运费'}}</span>
          <el-input v-model="shippingMethod.moenyPlu" maxlength="10"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="createArea()">确定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="添加包邮条件" :visible.sync="freeshipShowFlag">
      <el-form :model="shippingMethodFreeShip" :rules="freeShipRules" ref="shippingMethodFreeShipform"
               label-position="right" label-width="100px">
        <el-form-item>
          <span slot="label"><span class="labelImportant">*</span>收货地区</span>
          <el-tree
            style="max-height: 300px; overflow: auto; padding: 10px 0; border: 1px solid #DCDFE6; border-radius: 4px"
            ref="freeshipTree"
            :data="freeShipProvinces"
            show-checkbox
            default-expand-all
            node-key="id"
            :props="defaultProps"
          />
        </el-form-item>
        <el-form-item>
          <span slot="label">包邮类型</span>
          <el-select v-model="shippingMethodFreeShip.type" class="filter-item" size="medium">
            <el-option label="件数" value="0"/>
            <el-option label="金额" value="1"/>
          </el-select>
        </el-form-item>
        <el-form-item v-if="shippingMethodFreeShip.type == '0'" prop="num">
          <span slot="label">件数</span>
          <el-input v-model="shippingMethodFreeShip.num" maxlength="8"
                    style="width: 195px">
            <template slot="append">件</template>
          </el-input>
        </el-form-item>
        <el-form-item v-if="shippingMethodFreeShip.type == '1'" prop="money">
          <span slot="label">金额</span>
          <el-input v-model="shippingMethodFreeShip.money" maxlength="8"
                    style="width: 195px">
            <template slot="append">元</template>
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="freeshipShowFlag = false">取消</el-button>
        <el-button type="primary" @click="createFreeShip">确定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
  import {queryAllProvincesWithCity, addLogisticsTemplate} from '@/api/order/addlogisticstemplate';
  import {priceValidator, digitsValidator} from '@/utils/validate';

  export default {
    data() {
      return {
        freeshipShowFlag: false,// 包邮显示隐藏标记
        isProcess: false,// 接口是否处理中
        logisticsRules: {
          name: [
            {
              required: true,
              message: '请输入模版名称',
              trigger: 'blur'
            }
          ],
          companyId: [
            {
              required: true,
              message: '请选择运送方式',
              trigger: 'blur'
            }
          ],
          'shippingMethods[0].first': [
            {
              required: true,
              trigger: 'blur',
              validator: digitsValidator
            }
          ],
          'shippingMethods[0].money': [
            {
              required: true,
              trigger: 'blur',
              validator: priceValidator
            }
          ],
          'shippingMethods[0].firstPlu': [
            {
              required: true,
              trigger: 'blur',
              validator: digitsValidator
            }
          ],
          'shippingMethods[0].moenyPlu': [
            {
              required: true,
              trigger: 'blur',
              validator: priceValidator
            }
          ]
        }, // 物流模版的验证规则
        rules: {
          first: [
            {
              required: true,
              trigger: 'blur',
              validator: digitsValidator
            }
          ],
          money: [
            {
              required: true,
              trigger: 'blur',
              validator: priceValidator
            }
          ],
          firstPlu: [
            {
              required: true,
              trigger: 'blur',
              validator: digitsValidator
            }
          ],
          moenyPlu: [
            {
              required: true,
              trigger: 'blur',
              validator: priceValidator
            }
          ]
        }, // 收货地区的验证规则
        freeShipRules: {
          num: [
            {
              required: true,
              trigger: 'blur',
              validator: digitsValidator
            }
          ],
          money: [
            {
              required: true,
              trigger: 'blur',
              validator: priceValidator
            }
          ],
        }, // 包邮收货地区的验证规则
        shippingMethod: {
          areaNames: '',
          first: '1',
          firstPlu: '1',
          isDefault: '0',
          moenyPlu: '0',
          money: '0',
          shippingMethodAreas: [],
          index: '',
        },// 收获地区
        shippingMethods: [],// 已经设置的收货地区
        defaultProps: {
          children: 'child',
          label: 'name'
        },
        provinces: [{id: 0, name: "全部", child: []}],//省份信息
        freeShipProvinces: [{id: 0, name: "全部", child: []}],//包邮省份信息
        dialogFormVisible: false,// 添加收货人地区弹出框标记
        form: {
          companyId: '', //物流公司id
          freightBear: '0', //谁承担运费 0 买家 1 商家 默认 0 买家
          isDefault: '1',//是否默认模版 0 是 1 否 默认1
          name: '', //物流模版名称
          pricintMethod: '0', //计价方式 0 按件  1 按重量  默认0
          shippingMethods: [
            {first: '1', firstPlu: '1', isDefault: '1', moenyPlu: '0', money: '0'}
          ], //物流模版的运费方式
          shippingMethodFreeShips: [] // 包邮模版
        },// 添加的物流模版信息
        shippingMethodFreeShip: {
          areaNames: '',
          type: '1',
          num: '1',
          money: '0',
          shippingMethodFreeShipAreas: [],
          index: '',
        },// 包邮收货地区
        shippingMethodFreeShips: [],// 已经设置的包邮收货地区
      }
    },
    created() {
      this.queryProvinces();
    },
    methods: {
      queryProvinces() {
        queryAllProvincesWithCity().then(res => {
          this.provinces[0].child = JSON.parse(JSON.stringify(res));
          this.freeShipProvinces[0].child = JSON.parse(JSON.stringify(res));
        })
      },
      // 准备添加收货地区
      toAddArea() {
        if (this.$refs.tree) {
          this.$refs.tree.setCheckedKeys(new Array());
        }
        this.dialogFormVisible = true;

        if (this.$refs["shippingMethodform"]) {
          this.$refs["shippingMethodform"].resetFields();
        }

        this.shippingMethod = {
          areaNames: '',
          first: '1',
          firstPlu: '1',
          isDefault: '0',
          moenyPlu: '0',
          money: '0',
          shippingMethodAreas: [],
          index: '',
        };
      },
      // 准备添加包邮
      toAddFreeShip() {
        if (this.$refs.freeshipTree) {
          this.$refs.freeshipTree.setCheckedKeys(new Array());
        }
        this.freeshipShowFlag = true;
        this.shippingMethodFreeShip = {
          areaNames: '',
          type: '0',
          num: '',
          money: '',
          shippingMethodFreeShipAreas: [],
          index: '',
        }
      },
      // 添加包邮
      createFreeShip() {

        this.$refs['shippingMethodFreeShipform'].validate(valid => {
          if (valid) {
            if (!this.$refs.freeshipTree.getCheckedKeys() || this.$refs.freeshipTree.getCheckedKeys().length == 0) {
              this.$message({
                type: 'error',
                message: '请选择收货地区!'
              });
              return false;
            }
            this.$refs.freeshipTree.getCheckedNodes().forEach(node => {
              // 没有字节点的 才加入 也就是只加入市
              if (!(node.child && node.child.length > 0)) {
                // 已经选中的收货地区下次不能再选中
                node.disabled = true;
                this.shippingMethodFreeShip.areaNames += node.name + ',';
                this.shippingMethodFreeShip.shippingMethodFreeShipAreas.push({cityId: node.id});
              }
            })
            this.freeshipShowFlag = false;
            this.shippingMethodFreeShip.index = Date.parse(new Date());
            this.shippingMethodFreeShips.push(Object.assign({}, this.shippingMethodFreeShip));
            this.shippingMethodFreeShip = {
              areaNames: '',
              type: '0',
              num: '',
              money: '',
              shippingMethodFreeShipAreas: [],
              index: '',
            }
          }
        });
      },
      // 添加收货地区
      createArea() {
        this.$refs['shippingMethodform'].validate(valid => {
          if (valid) {
            if (!this.$refs.tree.getCheckedKeys() || this.$refs.tree.getCheckedKeys().length == 0) {
              this.$message({
                type: 'error',
                message: '请选择收货地区!'
              });
              return false;
            }

            this.$refs.tree.getCheckedNodes().forEach(node => {
              // 没有字节点的 才加入 也就是只加入市
              if (!(node.child && node.child.length > 0)) {
                // 已经选中的收货地区下次不能再选中
                node.disabled = true;
                this.shippingMethod.areaNames += node.name + ',';
                this.shippingMethod.shippingMethodAreas.push({cityId: node.id});
              }
            })
            this.dialogFormVisible = false;
            this.shippingMethod.index = Date.parse(new Date());
            this.shippingMethods.push(Object.assign({}, this.shippingMethod));
            this.shippingMethod = {
              areaNames: '',
              first: '1',
              firstPlu: '1',
              isDefault: '0',
              moenyPlu: '0',
              money: '0',
              shippingMethodAreas: [],
              index: '',
            };
          }
        });
      },
      // 删除包邮收货地区
      deleteFreeShip(shippingMethodFreeShip) {
        this.$confirm('确定要删除此收货地区吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.shippingMethodFreeShips = this.shippingMethodFreeShips.filter(item => item.index != shippingMethodFreeShip.index);
          // 将收货地区下面的所有市设置成可选择状态
          shippingMethodFreeShip.shippingMethodFreeShipAreas.forEach(area => {
            this.$refs.freeshipTree.getNode(area.cityId).data.disabled = false;
            this.$refs.freeshipTree.setCurrentKey(area.cityId);
          })
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        })
      },
      // 删除收货地区
      deleteArea(shippingMethod) {
        this.$confirm('确定要删除此收货地区吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.shippingMethods = this.shippingMethods.filter(item => item.index != shippingMethod.index);
          // 将收货地区下面的所有市设置成可选择状态
          shippingMethod.shippingMethodAreas.forEach(area => {
            this.$refs.tree.getNode(area.cityId).data.disabled = false;
            this.$refs.tree.setCurrentKey(area.cityId);
          })
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        })
      },
      // 保存默认模版
      saveLogistics() {
        this.$refs['logisticsform'].validate(valid => {
          if (valid && !this.isProcess) {
            this.isProcess = true;
            // 将选择的收货地区放入表单中
            this.shippingMethods.forEach(shippingMethod => {
              this.form.shippingMethods.push(shippingMethod);
            })

            this.form.shippingMethodFreeShips = this.shippingMethodFreeShips;
            addLogisticsTemplate(this.form).then(res => {
              this.isProcess = false;
              if (res.code==200) {
                this.$message({
                  type: 'success',
                  message: '创建成功！'
                });
                this.$router.push({path: '/order/delevierManager/OmsLogisticsTemplate'})
              } else if (res == -2) {
                this.$message({
                  type: 'error',
                  message: '运费模版名称已存在！'
                });
              } else {
                this.$message({
                  type: 'error',
                  message: '创建失败！'
                });
              }
            });
          }
        });
      }
    }
  }
</script>
