package com.banry.pscm.config;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class MvcConfig implements WebMvcConfigurer  {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    	registry.addViewController("/").setViewName("forward:/index");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/conf").setViewName("conf/conf");
    }
    
    @Bean
    public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
    	MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
    	//设置日期格式
    	ObjectMapper objectMapper = new ObjectMapper();
    	SimpleDateFormat smt = new SimpleDateFormat("yyyy-MM-dd");
    	objectMapper.setDateFormat(smt);
    	objectMapper.setSerializationInclusion(Include.NON_NULL);
    	mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
    	//设置中文编码格式
    	List<MediaType> list = new ArrayList<MediaType>();
    	list.add(MediaType.APPLICATION_JSON_UTF8);
    	mappingJackson2HttpMessageConverter.setSupportedMediaTypes(list);
    	return mappingJackson2HttpMessageConverter;
    }
    
//    @Bean
//    public HttpMessageConverters fastJsonHttpMessageConverters() {
//        //1、定义一个convert转换消息的对象
//        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
//
//        //2、添加fastjson的配置信息
//        FastJsonConfig fastJsonConfig = new FastJsonConfig();
//        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
//        SerializerFeature[] serializerFeatures = new SerializerFeature[]{
//                //    输出key是包含双引号
////                SerializerFeature.QuoteFieldNames,
//                //    是否输出为null的字段,若为null 则显示该字段
////                SerializerFeature.WriteMapNullValue,
//                //    数值字段如果为null，则输出为0
//                SerializerFeature.WriteNullNumberAsZero,
//                //     List字段如果为null,输出为[],而非null
//                SerializerFeature.WriteNullListAsEmpty,
//                //    字符类型字段如果为null,输出为"",而非null
//                SerializerFeature.WriteNullStringAsEmpty,
//                //    Boolean字段如果为null,输出为false,而非null
//                SerializerFeature.WriteNullBooleanAsFalse,
//                //    Date的日期转换器
//                SerializerFeature.WriteDateUseDateFormat,
//                //    循环引用
//                SerializerFeature.DisableCircularReferenceDetect,
//        };
//
//        fastJsonConfig.setSerializerFeatures(serializerFeatures);
//        fastJsonConfig.setCharset(Charset.forName("UTF-8"));
//
//        //3、在convert中添加配置信息
//        fastConverter.setFastJsonConfig(fastJsonConfig);
//        // 中文乱码解决方案
//        List<MediaType> mediaTypes = new ArrayList<>();
//        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);//设定json格式且编码为UTF-8
//        fastConverter.setSupportedMediaTypes(mediaTypes);
//
//        //4、将convert添加到converters中
//        HttpMessageConverter<?> converter = fastConverter;
//
//        return new HttpMessageConverters(converter);
//    }
}