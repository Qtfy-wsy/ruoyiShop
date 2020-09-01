<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
                                      <el-form-item label="名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
                                      <el-form-item label="轮播位置" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择轮播位置" clearable size="small">
          <el-option
            v-for="dict in typeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
                                          <el-form-item label="开始时间" prop="startTime">
        <el-date-picker clearable size="small" style="width: 200px"
          v-model="queryParams.startTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择开始时间">
        </el-date-picker>
      </el-form-item>
                                      <el-form-item label="结束时间" prop="endTime">
        <el-date-picker clearable size="small" style="width: 200px"
          v-model="queryParams.endTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择结束时间">
        </el-date-picker>
      </el-form-item>
                                      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable size="small">
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
                                          <el-form-item label="位置" prop="orderCount">
        <el-select v-model="queryParams.orderCount" placeholder="请选择位置" clearable size="small">
          <el-option
            v-for="dict in orderCountOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
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
          v-hasPermi="['sms:SmsHomeAdvertise:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['sms:SmsHomeAdvertise:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['sms:SmsHomeAdvertise:remove']"
        >删除</el-button>
      </el-col>

    </el-row>

    <el-table v-loading="loading" :data="SmsHomeAdvertiseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
                  <el-table-column label="编号" align="center" prop="id" />
                  <el-table-column label="名称" align="center" prop="name" />
                  <el-table-column label="轮播位置" align="center" prop="type" :formatter="typeFormat" />
                 <el-table-column label="商品图片" width="80">
                        <template slot-scope="scope">
                        <img :src="scope.row.pic" width="50"  height="50">
                        </template>
                  </el-table-column>
                  <el-table-column label="开始时间" align="center" prop="startTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
                  <el-table-column label="结束时间" align="center" prop="endTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
                  <el-table-column label="状态" align="center" prop="status" :formatter="statusFormat" />
                  <el-table-column label="点击数" align="center" prop="clickCount" />
                  <el-table-column label="位置" align="center" prop="orderCount" :formatter="orderCountFormat" />
                  <el-table-column label="链接地址" align="center" prop="url" />
                  <el-table-column label="备注" align="center" prop="note" />
                  <el-table-column label="排序" align="center" prop="sort" />

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['sms:SmsHomeAdvertise:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['sms:SmsHomeAdvertise:remove']"
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

    <!-- 添加或修改首页轮播广告对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
                                                            <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" />
        </el-form-item>
                                                                    <el-form-item label="轮播位置">
          <el-select v-model="form.type" placeholder="请选择轮播位置">
            <el-option
              v-for="dict in typeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
                                         <el-form-item label="广告图">
                                                <el-upload
                                                  class="avatar-uploader"
                                                  style="width: auto; height: auto"
                                                  :action="uploadApi"
                                                  :show-file-list="false"
                                                  :on-success="uploadPcPreSalePicSuccess"
                                                  :headers="headers"
                                                  name="image"
                                                  accept=",.JPG,.jpeg,.png,.gif,.ico,.JPEG,.PNG,.GIF,.ICO,.jpg">
                                                  <img v-if="form.pic" :src="form.pic" style="max-height: 100px; display: block">
                                                  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                                                </el-upload>
                                              </el-form-item>
                                                                    <el-form-item label="开始时间" prop="startTime">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.startTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择开始时间">
          </el-date-picker>
        </el-form-item>
                                                                    <el-form-item label="结束时间" prop="endTime">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.endTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择结束时间">
          </el-date-picker>
        </el-form-item>
                                                                    <el-form-item label="状态">
          <el-select v-model="form.status" placeholder="请选择状态">
            <el-option
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>

                                                                    <el-form-item label="位置">
          <el-select v-model="form.orderCount" placeholder="请选择位置">
            <el-option
              v-for="dict in orderCountOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
           <el-form-item label="链接地址" prop="url">
           <el-input v-model="form.url" @click="toEditLink" placeholder="请输入内容" />
           <el-button class="isLink"  style="width: 100%" type="primary"
                                      plain icon="el-icon-link"
                                      @click="toEditLink(form.url)"/>
        </el-form-item>
         <el-form-item label="备注" prop="note">
          <el-input v-model="form.note" type="textarea" placeholder="请输入内容" />
        </el-form-item>
                                                                    <el-form-item label="排序" prop="sort">
          <el-input v-model="form.sort" placeholder="请输入排序" />
        </el-form-item>

                  </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog title="链接" :visible.sync="dialogLinkVisible" width="80%">
          <el-tabs v-model="tabActiveName" @tab-click="changeTab()" type="card">
            <!-- 当前链接 -->
            <el-tab-pane label="当前链接" name="0" style="min-height: 300px">
              <div v-if="this.linkRef.actionType=='2'" style="line-height: 40px">分类：{{this.linkedCate.name}}</div>
              <div v-if="this.linkRef.actionType=='6'" style="line-height: 40px">功能链接：{{this.linkedFunction}}</div>
              <div v-if="this.linkRef.actionType=='7'" style="line-height: 40px">自定义链接：{{this.linkRef.actionParamId}}</div>
              <el-table
                v-if="this.linkRef.actionType =='1'"
                :data="linkedSkus"
                border
                fit
                highlight-current-row
              >
                <el-table-column label="图片" width="80">
                  <template slot-scope="scope"><img :src="scope.row.url" width="50" height="50"></template>
                </el-table-column>
                <el-table-column label="货品名称" min-width="250">
                  <template slot-scope="scope">{{ scope.row.name }}</template>
                </el-table-column>
                <el-table-column label="货品规格">
                  <template slot-scope="scope">
                    <span v-html="getSpecValues(scope.row.skuSpecValues)"></span>
                  </template>
                </el-table-column>
                <el-table-column label="销售价">
                  <template slot-scope="scope">{{ scope.row.price.toFixed(2) }}</template>
                </el-table-column>
                <el-table-column label="店铺名称">
                  <template slot-scope="scope">{{ scope.row.storeName }}</template>
                </el-table-column>
              </el-table>
              <el-table
                v-if="this.linkRef.actionType =='4'"
                :data="linkedThemtics"
                border
                fit
                highlight-current-row
              >
                <el-table-column label="专题标题" min-width="250">
                  <template slot-scope="scope">{{ scope.row.title }}</template>
                </el-table-column>
                <el-table-column label="是否启用" width="80">
                  <template slot-scope="scope">
                    <el-tag v-if="scope.row.isUse == '0'" type="success">是</el-tag>
                    <el-tag v-if="scope.row.isUse == '1'" type="info">否</el-tag>
                  </template>
                </el-table-column>
                <el-table-column label="创建时间" width="160">
                  <template slot-scope="scope">{{ scope.row.createTime }}</template>
                </el-table-column>
                <el-table-column label="更新时间" width="160">
                  <template slot-scope="scope">{{ scope.row.modifyTime }}</template>
                </el-table-column>
              </el-table>
              <el-table
                v-if="this.linkRef.actionType =='3'"
                :data="linkedArticle"
                border
                fit
                highlight-current-row
              >
                <el-table-column label="文章标题" min-width="250">
                  <template slot-scope="scope">{{ scope.row.title }}</template>
                </el-table-column>
                <el-table-column label="文章栏目">
                  <template slot-scope="scope">{{ scope.row.columnName }}</template>
                </el-table-column>
                <el-table-column label="是否发布" width="80">
                  <template slot-scope="scope">
                    <el-tag v-if="scope.row.isRelease == '0'" type="success">是</el-tag>
                    <el-tag v-if="scope.row.isRelease == '1'" type="info">否</el-tag>
                  </template>
                </el-table-column>
                <el-table-column label="更新时间" width="160">
                  <template slot-scope="scope">{{ scope.row.modifyTime?scope.row.modifyTime:'暂无修改信息'}}</template>
                </el-table-column>
              </el-table>
              <el-table
                v-if="this.linkRef.actionType =='5'"
                :data="linkedHelp"
                border
                fit
                highlight-current-row
              >
                <el-table-column label="帮助标题">
                  <template slot-scope="scope">{{ scope.row.name }}</template>
                </el-table-column>
                <el-table-column label="帮助分类">
                  <template slot-scope="scope">{{ scope.row.cateName }}</template>
                </el-table-column>
                <el-table-column label="是否显示">
                  <template slot-scope="scope">
                    <el-tag v-if="scope.row.isShow == '0'" type="success">是</el-tag>
                    <el-tag v-if="scope.row.isShow == '1'" type="info">否</el-tag>
                  </template>
                </el-table-column>
                <el-table-column label="排序">
                  <template slot-scope="scope">{{ scope.row.sort }}</template>
                </el-table-column>
              </el-table>
              <el-button class="filter-item" style="margin-top: 10px" type="primary" size="medium"
                         @click="clearChoosedLink">清除链接
              </el-button>
            </el-tab-pane>
            <!-- 商品 -->
            <el-tab-pane label="商品" name="1">
              <div class="filter-container">
                <el-input v-model="listQuery.name" maxlength="64" placeholder="商品名称" size="medium" style="width: 200px"
                          class="filter-item"/>
                <el-button class="filter-item" type="primary" size="medium" icon="el-icon-search" @click="handleQuery">搜索
                </el-button>
              </div>
              <el-table
                style="font-size: 13px"
                v-loading="listLoading"
                :data="skus"
                border
                fit
                highlight-current-row
              >
                <el-table-column align="center" width="40">
                  <template slot-scope="scope">
                    <el-radio class="table_radio" :label="scope.row.id" @change.native="selectSku(scope.row)"
                              v-model="selectedId">&nbsp;
                    </el-radio>
                  </template>
                </el-table-column>
                <el-table-column label="图片" width="80">
                  <template slot-scope="scope"><img :src="scope.row.url" width="50" height="50"></template>
                </el-table-column>
                <el-table-column label="货品名称" min-width="250">
                  <template slot-scope="scope">{{ scope.row.name }}</template>
                </el-table-column>
                <el-table-column label="货品规格">
                  <template slot-scope="scope">
                    <span v-html="getSpecValues(scope.row.skuSpecValues)"></span>
                  </template>
                </el-table-column>
                <el-table-column label="销售价">
                  <template slot-scope="scope">{{ scope.row.price.toFixed(2) }}</template>
                </el-table-column>
                <el-table-column label="店铺名称">
                  <template slot-scope="scope">{{ scope.row.storeName }}</template>
                </el-table-column>
              </el-table>

              <div class="pagination-container">
                <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange"
                               :current-page="listQuery.pageNum+1" :page-sizes="[10,20,30,50]"
                               :page-size="listQuery.pageSize"
                               layout="total, sizes, prev, pager, next, jumper" :total="total">
                </el-pagination>
              </div>
            </el-tab-pane>
            <!-- 分类 -->
            <el-tab-pane label="分类" name="2">
              <el-tree
                v-model="selectCateId"
                style="max-height: 300px; overflow: auto; padding: 10px 0; border: 1px solid #DCDFE6; border-radius: 4px"
                @check="checkCate"
                :props="props"
                :load="loadNode"
                ref="tree"
                :check-strictly="true"
                :default-expand-all="true"
                lazy
                node-key="id"
                show-checkbox>
              </el-tree>
            </el-tab-pane>
            <!-- 文章 -->
            <el-tab-pane label="文章" name="3" style="min-height: 300px">
              <div class="filter-container">
                <el-input placeholder="文章标题" maxlength="32" v-model="articleListQuery.title" size="medium"
                          style="width: 200px;" class="filter-item"/>
                <el-button class="filter-item" type="primary" size="medium" icon="el-icon-search"
                           @click="handleArticleFilter">搜索
                </el-button>
              </div>

              <el-table
                style="font-size: 13px"
                v-loading="articleListLoading"
                :data="articles"
                border
                fit
                highlight-current-row
              >
                <el-table-column align="center" width="40">
                  <template slot-scope="scope">
                    <el-radio class="table_radio" :label="scope.row.id" @change.native="selectArticle(scope.row)"
                              v-model="selectedHelpId">&nbsp;
                    </el-radio>
                  </template>
                </el-table-column>
                <el-table-column label="文章标题" min-width="250">
                  <template slot-scope="scope">{{ scope.row.title }}</template>
                </el-table-column>
                <el-table-column label="文章栏目">
                  <template slot-scope="scope">{{ scope.row.columnName }}</template>
                </el-table-column>
                <el-table-column label="是否发布" width="80">
                  <template slot-scope="scope">
                    <el-tag v-if="scope.row.isRelease == '0'" type="success">是</el-tag>
                    <el-tag v-if="scope.row.isRelease == '1'" type="info">否</el-tag>
                  </template>
                </el-table-column>
                <el-table-column label="更新时间" width="160">
                  <template slot-scope="scope">{{ scope.row.modifyTime?scope.row.modifyTime:'暂无修改信息'}}</template>
                </el-table-column>
              </el-table>

              <div class="pagination-container">
                <el-pagination background @size-change="handleArticleSizeChange"
                               @current-change="handleArticleCurrentChange"
                               :current-page="articleListQuery.pageNum+1" :page-sizes="[10,20,30,50]"
                               :page-size="articleListQuery.pageSize"
                               layout="total, sizes, prev, pager, next, jumper" :total="articleTotal">
                </el-pagination>
              </div>


            </el-tab-pane>
            <!-- 专题 -->

            <!-- 帮助 -->
            <el-tab-pane label="帮助" name="5" style="min-height: 300px">
              <div class="filter-container">
                <el-input placeholder="帮助标题" maxlength="32" v-model="helpListQuery.name" size="medium" style="width: 200px;"
                          class="filter-item"/>
                <el-button class="filter-item" type="primary" size="medium" icon="el-icon-search" @click="handleHelpFilter">
                  搜索
                </el-button>
              </div>

              <el-table
                style="font-size: 13px"
                v-loading="helpListLoading"
                :data="helps"
                border
                fit
                highlight-current-row
              >
                <el-table-column align="center" width="40">
                  <template slot-scope="scope">
                    <el-radio class="table_radio" :label="scope.row.id" @change.native="selectHelp(scope.row)"
                              v-model="selectedHelpId">&nbsp;
                    </el-radio>
                  </template>
                </el-table-column>
                <el-table-column label="帮助标题">
                  <template slot-scope="scope">{{ scope.row.name }}</template>
                </el-table-column>
                <el-table-column label="帮助分类">
                  <template slot-scope="scope">{{ scope.row.cateName }}</template>
                </el-table-column>
                <el-table-column label="是否显示">
                  <template slot-scope="scope">
                    <el-tag v-if="scope.row.isShow == '0'" type="success">是</el-tag>
                    <el-tag v-if="scope.row.isShow == '1'" type="info">否</el-tag>
                  </template>
                </el-table-column>
                <el-table-column label="排序">
                  <template slot-scope="scope">{{ scope.row.sort }}</template>
                </el-table-column>
              </el-table>

              <div class="pagination-container">
                <el-pagination background @size-change="handleHelpSizeChange"
                               @current-change="handleHelpCurrentChange"
                               :current-page="helpListQuery.pageNum+1" :page-sizes="[10,20,30,50]"
                               :page-size="helpListQuery.pageSize"
                               layout="total, sizes, prev, pager, next, jumper" :total="helpTotal">
                </el-pagination>
              </div>
            </el-tab-pane>
            <!-- 功能链接 -->
            <el-tab-pane label="功能链接" name="6" style="min-height: 300px">
              <el-select class="filter-item" v-model="functionUrl" size="medium" style="width: 100%">
                <el-option label="未选择" value="-1"></el-option>
                <el-option label="领券中心" value="/pages/user/coupon/list"></el-option>
                <el-option label="红包中心" value="/pages/user/red/list"></el-option>
                <el-option label="秒杀" value="/pages/marketing/skillMarking"></el-option>
                <el-option label="预售中心" value="/pages/marketing/preMarking"></el-option>
                <el-option label="试用中心" value="/pages/marketing/tryMarking"></el-option>
                <el-option label="积分商城" value="/pages/product/jifenList"></el-option>
                <el-option label="拼团" value="/pages/marketing/groupMarking"></el-option>
                <el-option label="砍价" value="/pages/marketing/kanMarking"></el-option>
              </el-select>
            </el-tab-pane>
            <!-- 自定义 -->
            <el-tab-pane label="自定义" name="7" style="min-height: 300px">
              <el-input placeholder="请输入自定义链接地址" size="medium" style="width: 100%" class="filter-item"
                        v-model="customizeUrl"/>
            </el-tab-pane>
          </el-tabs>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogLinkVisible = false">取消</el-button>
            <el-button type="primary" @click="saveLink">确定</el-button>
          </div>
        </el-dialog>
  </div>
</template>

<script>
 import {Input} from "element-ui";
 import {
    queryTemplateSkus,
    queryArticleList,
    queryThematic,
    queryHelpList,
    queryAllCategory,
    querySkuById,
    queryArticleById,
    queryThematicById,
    queryHelpById,
    queryTemplate,
    updateTemplate
  } from '@/api/templateone';
import { listSmsHomeAdvertise, getSmsHomeAdvertise, delSmsHomeAdvertise, addSmsHomeAdvertise, updateSmsHomeAdvertise, exportSmsHomeAdvertise } from "@/api/sms/SmsHomeAdvertise";
  import {getToken} from '@/utils/auth'
   // 功能链接映射关系
    const functionUrlMapping = {
      '1': '领券中心',
      '2': '红包中心',
      '3': '秒杀',
      '4': '预售中心',
      '5': '试用中心',
      '6': '积分商城',
      '7': '众筹项目'
    }
export default {
  name: "SmsHomeAdvertise",
   components: {
        Input,

      },

  data() {
    return {
    headers: { // 设置上传的请求头部
              Authorization: 'Bearer ' + getToken()
            },
     uploadApi: process.env.VUE_APP_BASE_API+'/aliyun/oss/uploadToAliOss',// 上传文件路由
      // 遮罩层
      loading: true,
        dialogLinkVisible: false, // 链接编辑显示隐藏标记

                listQuery: {
                          pageNum: 0,
                          pageSize: 10,
                          name: '',
                          skuNo: '',
                        }, // 查询参数
                        skus: null, // 单品数据
                        total: null, // 数据总数,
                        tempSelectedSku: {},// 临时选中的sku信息
                        selectedId: '',// 选中的单品id
                        articleListQuery: {
                          pageNum: 0,
                          pageSize: 10,
                          title: '',
                        }, // 查询参数
                        articleListLoading: true, // 加载中
                        articles: null, // 文章数据
                        articleTotal: null, // 数据总数,
                        selectedArticleId: '',// 选中的文章id
                        tempSelectedArticle: {},// 临时选中的文章
                        helpListQuery: {
                          pageNum: 0,
                          pageSize: 10,
                          name: '',
                        }, // 查询参数
                        helpListLoading: true, // 加载中
                        helps: null, // 帮助数据
                        helpTotal: null, // 数据总数,
                        selectedHelpId: '',// 选中的帮助id
                        tempSelectedHelp: {},// 临时选中的帮助
                        selectCateId: 0,//选中的分类id
                        selectCateGrade:0,
                        allCates: [],// 所有的分类
                        firstCates: [],// 所有的一级分类
                         thematicListQuery: {
                                  pageNum: 0,
                                  pageSize: 10,
                                  name: '',
                                }, // 查询参数
                                thematicListLoading: true, // 加载中
                                thematics: null, // 专题数据
                                thematicTotal: null, // 专题数据总数,
                                selectedThematicId: '',// 选中的专题id
                                tempSelectedThematic: {},// 临时选中的专题
                                customizeUrl: '',// 自定义链接地址
                                linkRef: {
                                  actionType: '',
                                  actionParamId: ''
                                },// 编辑链接的引用
                                linkedSkus: [],// 链接选中的单品
                                linkedThemtics: [],//链接选中的专题
                                linkedCate: {},// 链接选中的分类
                                linkedHelp: [], // 链接选中的帮助
                                linkedArticle: [],// 链接选中的文章
                                linkedFunction: '',// 链接选中的功能链接
                                functionUrl: '-1', // 当前选中的功能链接
                                props: {
                                  label: 'name',
                                  children: 'zones',
                                  isLeaf: 'leaf'
                                },
                                 tabActiveName: '0',// 当前选中的tab
                                 listLoading:false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 首页轮播广告表格数据
      SmsHomeAdvertiseList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
                                          // 轮播位置字典
        typeOptions: [],
                                                      // 状态字典
        statusOptions: [],
                              // 位置字典
        orderCountOptions: [],
                                                      // 查询参数
      queryParams: {
        pageNum: 0,
        pageSize: 10,
                name: undefined,
                type: undefined,
                    startTime: undefined,
                endTime: undefined,
                status: undefined,
                    orderCount: undefined,
                            storeId: undefined
          },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
                                                                            storeId: [
          { required: true, message: "广告所属店铺不能为空", trigger: "blur" }
        ]
          }
    };
  },
  created() {
    this.getList();
     this.queryAllCates();
                this.getDicts("banner_type").then(response => {
      this.typeOptions = response.data;
    });
                    this.getDicts("sys_show_hide").then(response => {
      this.statusOptions = response.data;
    });
            this.getDicts("bannerLocation").then(response => {
      this.orderCountOptions = response.data;
    });
                  },

                      computed: {
                        getSpecValues() {
                          return function (skuSpecValues) {
                            let content = '';

                            for (let i = 0; i < skuSpecValues.length; i++) {
                              content += skuSpecValues[i].spec.name + ":" + skuSpecValues[i].valueRemark + "<br>";
                            }
                            return content;
                          }
                        }
                      },
  methods: {
    /** 查询首页轮播广告列表 */
    getList() {
      this.loading = true;
      listSmsHomeAdvertise(this.queryParams).then(response => {
        this.SmsHomeAdvertiseList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
                                // 轮播位置字典翻译
        typeFormat(row, column) {
      return this.selectDictLabel(this.typeOptions, row.type);
    },
     // 上传PC预售广告图回调
              uploadPcPreSalePicSuccess(res) {
                this.form.pic = res;
              },
                                    // 状态字典翻译
        statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
                            // 位置字典翻译
        orderCountFormat(row, column) {
      return this.selectDictLabel(this.orderCountOptions, row.orderCount);
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
                name: undefined,
                type: undefined,
                pic: undefined,
                startTime: undefined,
                endTime: undefined,
                status: undefined,
                clickCount: undefined,
                orderCount: undefined,
                url: undefined,
                note: undefined,
                sort: undefined,
                storeId: undefined
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
      this.title = "添加首页轮播广告";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getSmsHomeAdvertise(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改首页轮播广告";
      });
    },
    /** 提交按钮 */
        submitForm: function() {
          this.$refs["form"].validate(valid => {
            if (valid) {
              if (this.form.id != undefined) {
                updateSmsHomeAdvertise(this.form).then(response => {
                  if (response.code === 200) {
                    this.msgSuccess("修改成功");
                    this.open = false;
                    this.getList();
                  }
                });
              } else {
                addSmsHomeAdvertise(this.form).then(response => {
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
          this.$confirm('是否确认删除首页轮播广告编号为"' + ids + '"的数据项?', "警告", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning"
            }).then(function() {
              return delSmsHomeAdvertise(ids);
            }).then(() => {
              this.getList();
              this.msgSuccess("删除成功");
            }).catch(function() {});
        },
        /** 导出按钮操作 */
        handleExport() {
          const queryParams = this.queryParams;
          this.$confirm('是否确认导出所有首页轮播广告数据项?', "警告", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning"
            }).then(function() {
              return exportSmsHomeAdvertise(queryParams);
            }).then(response => {
              this.download(response.msg);
            }).catch(function() {});
        },


      // 编辑链接
          toEditLink(link) {
          this.clearLink();
            this.tabActiveName = '0';
            this.dialogLinkVisible = true
            this.linkedSkus = []
                    this.linkedThemtics = []
                    this.linkedCate = {}
                    this.linkedHelp = []
                    this.linkedArticle = []
                    this.linkedFunction = ''
          },
            // 清除选中的链接
                clearLink() {
                  this.tempSelectedSku = {};
                  this.selectedId = '';
                  this.selectCateId = 0;
                  this.tempSelectedThematic = {};
                  this.selectedThematicId = '';
                  this.selectedArticleId = '';
                  this.tempSelectedArticle = {};
                  this.selectedHelpId = '';
                  this.tempSelectedHelp = {};
                  this.functionUrl = '-1';
                  this.customizeUrl = '';
                },
                // 保存链接
                saveLink() {

                  // 选中的是自定义链接，不能为空
                  if (this.tabActiveName == '7' && !this.customizeUrl) {
                    this.showError('自定义链接不能为空！');
                    return;
                  }
                  // 当前选中的是商品
                  if (this.selectedId != '') {
                    this.linkRef.actionType = '1';
                    this.linkRef.actionParamId = this.selectedId;
                    this.form.url='/pages/product/product?id='+this.tempSelectedSku.spuId
                  } else if (this.selectCateId != 0) {
                    // 当前选中的是分类
                    this.linkRef.actionType = '2';
                    this.linkRef.actionParamId = this.selectCateId;
                    if(this.selectCateGrade==1){
                      this.form.url='/pages/product/list?firstCateId='+this.selectCateId
                    }else  if(this.selectCateGrade==2){
                                                this.form.url='/pages/product/list?secondCateId='+this.selectCateId
                                              }else  if(this.selectCateGrade==3){
                                                                          this.form.url='/pages/product/list?thirdCateId='+this.selectCateId
                                                                        }

                  } else if (this.selectedArticleId != '') {
                    // 当前选中的是文章
                    this.linkRef.actionType = '3';
                    this.linkRef.actionParamId = this.selectedArticleId;
                     this.form.url='/pages/set/article/detail?id='+this.selectedArticleId
                  } else if (this.selectedThematicId != '') {
                    // 当前选中的是专题
                    this.linkRef.actionType = '4';
                    this.linkRef.actionParamId = this.selectedThematicId;
                  } else if (this.selectedHelpId != '') {
                    // 当前选中的是帮助
                    this.linkRef.actionType = '5';
                    this.linkRef.actionParamId = this.selectedHelpId;
                    this.form.url='/pages/set/helper/detail?id='+this.selectedHelpId
                  } else if (this.functionUrl != '-1') {
                    // 选择的是功能链接
                    this.linkRef.actionType = '6';
                    this.linkRef.actionParamId = this.functionUrl;
                     this.form.url=this.functionUrl;
                  } else if (this.customizeUrl != '') {
                    // 选择的是自定义链接
                    this.linkRef.actionType = '7';
                    this.linkRef.actionParamId = this.customizeUrl;
                     this.form.url=this.customizeUrl;
                  }
                console.log(this.linkRef)

                  this.dialogLinkVisible = false
                  this.$refs.tree.setCheckedKeys([]);
                },
                // 清除已经选中的链接
                clearChoosedLink() {
                  this.linkRef.actionType = '';
                  this.linkRef.actionParamId = '';
                },

      toLink(link) {
        this.$router.push({path: link})
      },
      // 改变tab
      changeTab() {
        // 选中的是商品查询商品信息
        if (this.tabActiveName == '1') {
          this.querySkus();
        } else  if (this.tabActiveName == '2') {

                       }else if (this.tabActiveName == '3') {
          //选中的是文章查询
          this.queryArticleList();
        } else if (this.tabActiveName == '4') {
          // 专题查询
          this.queryThematicList();
        } else if (this.tabActiveName == '5') {
          // 帮助查询
          this.queryHelpList();
        }
      },
      // 查询单品
      querySkus() {
        this.listLoading = true
        queryTemplateSkus(this.listQuery).then(response => {
          this.skus = response.data;
          this.total = response.recordsTotal;
          this.listLoading = false
        });
      },
      // 选中单品
      selectSku(sku) {
        this.selectedId = sku.id;
        this.tempSelectedSku = sku;
      },
      // 过滤查询
      handleQuery() {
        this.listQuery.pageNum = 0;
        this.querySkus();
      },
      handleSizeChange(val) {
        this.listQuery.pageNum = 0;
        this.listQuery.pageSize = val;
        this.querySkus();
      },
      handleCurrentChange(val) {
        this.listQuery.pageNum = val - 1;
        this.querySkus();
      },
      // 查询文章
      queryArticleList() {
        this.articleListLoading = true
        queryArticleList(this.articleListQuery).then(response => {
          this.articles = response.data;
          this.articleTotal = response.recordsTotal;
          this.articleListLoading = false
        });
      },
      // 过滤查询
      handleArticleFilter() {
        this.articleListQuery.pageNum = 0;
        this.queryArticleList();
      },
      handleArticleSizeChange(val) {
        this.articleListQuery.pageNum = 0;
        this.articleListQuery.pageSize = val;
        this.queryArticleList();
      },
      handleArticleCurrentChange(val) {
        this.articleListQuery.pageNum = val - 1;
        this.queryArticleList();
      },
      // 选中文章
      selectArticle(article) {
        this.selectedArticleId = article.id;
        this.tempSelectedArticle = article;
      },
      // 查询帮助
      queryHelpList() {
        this.helpListLoading = true
        queryHelpList(this.helpListQuery).then(response => {
          this.helps = response.data;
          this.helpTotal = response.recordsTotal;
          this.helpListLoading = false
        });
      },
      // 过滤查询
      handleHelpFilter() {
        this.helpListQuery.pageNum = 0;
        this.queryHelpList();
      },
      handleHelpSizeChange(val) {
        this.helpListQuery.pageNum = 0;
        this.helpListQuery.pageSize = val;
        this.queryHelpList();
      },
      handleHelpCurrentChange(val) {
        this.helpListQuery.pageNum = val - 1;
        this.queryHelpList();
      },
      // 选中帮助
      selectHelp(help) {
        this.selectedHelpId = help.id;
        this.tempSelectedHelp = help;
      },

      // 查询专题
      queryThematicList() {
        this.thematicListLoading = true
        queryTemplateThematicList(this.thematicListQuery).then(response => {
          this.thematics = response.data;
          this.thematicTotal = response.recordsTotal;
          this.thematicListLoading = false
        });
      },
      // 过滤查询
      handleThematicFilter() {
        this.thematicListQuery.pageNum = 0;
        this.queryThematicList();
      },
      handleThematicSizeChange(val) {
        this.thematicListQuery.pageNum = 0;
        this.thematicListQuery.pageSize = val;
        this.queryThematicList();
      },
      handleThematicCurrentChange(val) {
        this.thematicListQuery.pageNum = val - 1;
        this.queryThematicList();
      },
      // 选中专题
      selectThematic(thematic) {
        this.selectedThematicId = thematic.id;
        this.tempSelectedThematic = thematic;
      },

      //选择boss分类
      checkCate(even) {
        this.$refs.tree.setCheckedKeys([]);
        this.$refs.tree.setChecked(even.id, true, false)

        this.selectCateId = even.id;
        this.selectCateGrade = even.grade;
      },
      //加载 el-tree 数据
      loadNode(node, resolve) {

        if (node.level === 0) {
          return resolve(this.firstCates);
        }
        if (node.level == 1 || node.level == 2) {
          let second = new Array();
          for (let i = 0; i < this.allCates.length; i++) {
            if (this.allCates[i].parentId == node.data.id) {
              let s = {
                id: this.allCates[i].id,
                name: this.allCates[i].name,
                grade: this.allCates[i].grade,
              }
              second.push(s);
            }
          }
          return resolve(second);
        }
        if (node.level == 3) {
          return resolve([]);
        }
      },
      // 查询所有分类
      queryAllCates() {
        queryAllCategory().then(res => {
          if (res) {
            this.allCates = res;
            for (let i = 0; i < res.length; i++) {
              if (res[i].grade == 1) {
                this.firstCates.push({
                  id: res[i].id,
                  name: res[i].name,
                  grade: this.allCates[i].grade,
                });
              }
            }
          }
        })
      },
          }
};
</script>
