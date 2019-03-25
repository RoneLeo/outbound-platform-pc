package com.chiyun.outboundplatform.utils;

/**
 * Created by Linqi on 2019-03-25.
 */
public class PageUtil {

    /**
     * 获取总页数
     * @param counts    条目总数
     * @param pageSize  每页显示条目数
     * @return
     */
    public  static int  getTotalPage( int counts ,int pageSize){
            int  num=0;
          if(  pageSize/counts ==0){
              num= pageSize/counts;
          }else{
              num= pageSize/counts+1;
          }
          return   num;
      }

    /**
     * 获取当前页的起始值
     * @param page    第几页
     * @param pageSize  每页显示条数
     * @return
     */
    public  static  int   getPageStratNumber(int page,int pageSize){
          if(page==0){
              return  page*pageSize;
          }

        return  (page-1)*pageSize;
    }
    /**
     * 获取当前页的结束值
     * @param page    第几页
     * @param pageSize  每页显示条数
     * @return
     */
    public   static  int   getPageEndNumber(int page,int pageSize){

        if(page==0){
           page=page+1;
        }

        return  page*pageSize;
    }

}
