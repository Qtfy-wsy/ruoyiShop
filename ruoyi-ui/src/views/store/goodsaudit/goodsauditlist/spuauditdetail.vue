<template>
  <div class="app-container">
    <el-form label-position="right" label-width="100px" style="margin-top: 20px">
      <el-form-item label="商品标题">{{spu.name}}</el-form-item>
      <el-form-item label="商品副标题">{{spu.subTitle}}</el-form-item>
      <el-form-item label="服务支持">{{serviceSupport}}</el-form-item>
      <el-form-item label="销售价格">¥{{spu.price?spu.price.toFixed(2):0}}</el-form-item>
      <el-form-item label="是否上架">{{spu.shelvesStatus =='1'?'是':'否'}}</el-form-item>
      <el-form-item label="是否虚拟商品">{{spu.isVirtual =='1'?'是':'否'}}</el-form-item>
      <el-form-item label="商品品牌">{{spu.brand?spu.brand.name:''}}</el-form-item>
      <el-form-item label="商品属性">
        <el-form :inline="true" class="demo-form-inline"
                 style="padding: 10px 10px 0; border-radius: 4px; border: dashed 1px #ccc">
          <el-form-item v-for="item in spu.spuAttributeValues" :key="item.id" :label="item.attributeName"
                        label-width="95px"
                        style="margin-bottom: 10px">
            <div style="width: 195px">{{ item.attributeValue }}</div>
          </el-form-item>
        </el-form>
      </el-form-item>
      <el-form-item label="商品图片">
        <el-image v-for="image in spu.spuImages" :key="image.id" :src="image.url"
                  style="width: 90px; height: 90px; margin-right: 10px" fit="fill"></el-image>
      </el-form-item>
      <el-form-item label="规格">
        <el-table
          :data="spu.skus"
          border
          fit
          highlight-current-row
        >
          <el-table-column :key="specName" v-for=" (specName,index) in selectedSpecNames" :label="specName">
            <template slot-scope="scope">{{ scope.row.skuSpecValues[index].valueRemark }}</template>
          </el-table-column>
          <el-table-column label="重量(克)">
            <template slot-scope="scope">{{ scope.row.weight }}</template>
          </el-table-column>
          <el-table-column label="价格(元)">
            <template slot-scope="scope">{{ scope.row.price?scope.row.price.toFixed(2):0 }}</template>
          </el-table-column>
          <el-table-column label="库存">
            <template slot-scope="scope">{{ scope.row.stock }}</template>
          </el-table-column>
          <el-table-column label="状态">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.shelvesStatus == '1'" type="success">上架</el-tag>
              <el-tag v-if="scope.row.shelvesStatus == '0'" type="danger">下架</el-tag>
              <el-tag v-if="scope.row.shelvesStatus == '2'" type="danger">违规下架</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="审核">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.status == '0'" type="success">通过</el-tag>
              <el-tag v-if="scope.row.status == '1'" type="danger">拒绝</el-tag>
              <el-tag v-if="scope.row.status == '2'" type="warning">审核中</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="105">
            <template slot-scope="scope">
              <el-button  type="text" icon="el-icon-edit" size="mini" @click="toShowSkuDetail(scope.row)">查看详情</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-form-item>
      <el-form-item label="PC版详情">
        <div class="htmlContent" v-html="spu.pcDesc"></div>
      </el-form-item>
      <el-form-item label="移动版详情">
        <div class="htmlContent" v-html="spu.mobileDesc"></div>
      </el-form-item>
      <!--<el-form-item label="seo标题">{{spu.seoTitle}}</el-form-item>-->
      <!--<el-form-item label="seo关键字">{{spu.seoKeywords}}</el-form-item>-->
      <!--<el-form-item label="seo描述">{{spu.seoDesc}}</el-form-item>-->
      <el-form-item>
        <el-button type="info" icon="el-icon-back" @click="backPage">返回商品列表</el-button>
        <el-button type="primary" @click="passDialog">审核通过</el-button>
        <el-button type="danger" @click="auditShow = true">审核不通过</el-button>
      </el-form-item>
    </el-form>

    <el-dialog title="查看规格详情" :visible.sync="skuDetailShow">
      <el-form label-position="right" label-width="100px" style="margin-top: 10px">
        <el-form-item label="SKU标题">{{currentSku.name}}</el-form-item>
        <el-form-item label="SKU副标题">{{currentSku.subTitle}}</el-form-item>
        <el-form-item label="SKU图片">
          <el-image v-for="image in currentSku.skuImages" :key="image.id" :src="image.url"
                    style="width: 90px; height: 90px; margin-right: 10px" fit="fill"></el-image>
        </el-form-item>
        <el-form-item label="SKU编号">{{currentSku.id}}</el-form-item>
        <el-form-item label="预警库存">{{currentSku.warningStock=='-1'?'':currentSku.warningStock}}</el-form-item>
      </el-form>
    </el-dialog>

    <el-dialog title="审核不通过" :visible.sync="auditShow">
      <el-form label-position="right" label-width="100px" style="margin-top: 10px">
        <el-form-item label="拒绝原因">
          <el-input type="textarea" v-model="reason" maxlength="2048" :rows="3"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="auditShow = false">取消</el-button>
        <el-button type="danger" @click="refuse()">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {
    querySpuByIdForStore,
    queryAllServiceSupportForstore,
    auditPass,
    auditRefuse
  } from '@/api/store/spuauditdetail';

  export default {
    data() {
      return {
        isProcess: false, // 接口是否处理中
        reason: '',//审核不通过原因
        spuId: '',// 商品id
        auditShow: false,// 审核不通过显示隐藏标记
        serviceSupport: '',// 服务支持
        skuDetailShow: false,// 单品详情页显示标记
        selectedSpecNames: [],// 已经选择的规格名称
        spu: {},// 商品信息
        currentSku: {},// 当前选中的sku信息
      }
    },
    created() {
      this.querySpu()
    },
    methods: {
      // 查看单品详情
      toShowSkuDetail(sku) {
        this.currentSku = sku;
        this.skuDetailShow = true;
      },
      // 查询所有服务支持
      queryAllServiceSupportForstore() {
        queryAllServiceSupportForstore().then(res => {
          let supportName = '';
          this.spu.spuServiceSupports.forEach(spuServiceSupport => {
            const support = res.filter(x => x.id == spuServiceSupport.serviceSupportId)[0];
            if (support) {
              supportName += support.name + '  ';
            }
          })
          this.serviceSupport = supportName;
        })
      }
      ,
      // 查询商品信息
      querySpu() {
        querySpuByIdForStore(this.$route.query.id).then(res => {
          this.spu = res;
          this.selectedSpecNames = Array.from(new Set(res.spuSpecValues.map(specValue => {
            return specValue.spec.name;
          })))
          if (this.spu.spuServiceSupports.length > 0) {
            this.queryAllServiceSupportForstore();
          }
        })
      },
      backPage() {
        this.$router.back(-1)
      },
      // 商品审核通过
      passDialog() {
        this.$confirm('确定要通过此商品审核吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          if (this.isProcess) {
            return;
          }
          this.isProcess = true;
          auditPass(this.spu.id).then(() => {
            this.isProcess = false;
            this.$message({
              type: 'success',
              message: '审核商品通过成功!'
            })
            this.$router.back(-1)
          })
        })
      },
      // 审核不通过
      refuse() {
        if (this.isProcess) {
          return;
        }
        this.isProcess = true;
        auditRefuse(this.spu.id, this.reason).then(() => {
          this.isProcess = false;
          this.$message({
            type: 'success',
            message: '审核商品不通过成功!'
          })
          this.$router.back(-1)
        })
      }
    }
  }
</script>
