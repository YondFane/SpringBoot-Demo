package com.yfan.springboot.elasticsearch;

import com.alibaba.fastjson.JSON;
import com.yfan.springboot.elasticsearch.pojo.User;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class SpringBootElasticsearchApplicationTests {

    @Resource
    private RestHighLevelClient client;

    private final String ES_INDEX = "elasticsearch_demo";

    @Test
    void contextLoads() throws IOException {
    }

    /*
     * 创建索引
     */
    @Test
    void createIndex() throws IOException {
        // 1、创建索引请求
        CreateIndexRequest indexRequest = new CreateIndexRequest(ES_INDEX);

        // 2、客户端执行创建请求
        CreateIndexResponse createIndexResponse = client.indices().create(indexRequest, RequestOptions.DEFAULT);
        System.out.println("创建请求响应：");
        System.out.println(createIndexResponse);
    }
    // 判断索引是否存在
    @Test
    void existsIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest(ES_INDEX);
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println("索引是否存在");
        System.out.println(exists);
    }
    // 删除索引
    @Test
    void deleteIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest(ES_INDEX);
        AcknowledgedResponse acknowledgedResponse = client.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println("删除索引");
        System.out.println(acknowledgedResponse.isAcknowledged());
    }

    // 创建文档
    @Test
    void createDocument() throws IOException {
        // 创建对象
        User user = new User("张三" , 18);
        // 创建请求
        IndexRequest request = new IndexRequest(ES_INDEX);
        // 规则
        request.id("1");
        request.timeout(TimeValue.timeValueSeconds(1));
        request.source(JSON.toJSON(user), XContentType.JSON);
        // 客户端发送请求
        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
        System.out.println(indexResponse.toString());
        System.out.println(indexResponse.status());
    }
    // 获取文档
    @Test
    void getDocument() throws IOException {
        GetRequest request = new GetRequest(ES_INDEX, "1");
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        System.out.println("获取文档");
        System.out.println(response.toString());
        System.out.println(response.isExists());
        System.out.println(response.getSource());
        System.out.println(response.getSourceAsString());
    }
    // 更新文档
    @Test
    void updateDocument() throws IOException {
        UpdateRequest request = new UpdateRequest(ES_INDEX, "1");
        request.doc(JSON.toJSON(new User("张三2", 20)), XContentType.JSON);
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        System.out.println("更新文档");
        System.out.println(response.toString());
        System.out.println(response.status());
    }

    // 删除文档
    @Test
    void deleteDocument() throws IOException {
        DeleteRequest request = new DeleteRequest(ES_INDEX, "1");
        request.timeout("1s");
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        System.out.println("删除文档");
        System.out.println(response.toString());
        System.out.println(response.status());
    }

    // 批量创建文档
    @Test
    void batchCreateDocument() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("10s");
        for (int i = 20; i < 40; i++) {
            User user = new User("TestUser" + i, i + 10);
            bulkRequest.add(
                    new IndexRequest(ES_INDEX)
                    .id("TEST"+i)
//                    .source(JSON.toJSONString(user), XContentType.JSON)
                            .source(XContentType.JSON,"name","TestUser" + i,"age",12+i)
            );
            System.out.println(JSON.toJSONString(user));
        }
        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println("批量创建文档");
        System.out.println(bulkResponse.toString());
        System.out.println(bulkResponse.status());
    }
    // 查询
    @Test
    void searchDocument() throws IOException {
        SearchRequest searchRequest = new SearchRequest(ES_INDEX);
        // 构建搜索条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 查询条件可以用QueryBuilders工具类来实现
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "1");
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("name", "TestUser");
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        sourceBuilder.query(matchAllQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(searchResponse.toString());
        System.out.println(JSON.toJSONString(searchResponse.getHits()));
        System.out.println(searchResponse.getHits());
        searchResponse.getHits().forEach(o->System.out.println(o.getSourceAsString()));

    }
}
