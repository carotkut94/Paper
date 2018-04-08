package com.death.paper.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
/**
 * Awesome Pojo Generator
 * */
public class News {
  @SerializedName("sortBy")
  @Expose
  private String sortBy;
  @SerializedName("source")
  @Expose
  private String source;
  @SerializedName("articles")
  @Expose
  private List<Articles> articles;
  @SerializedName("status")
  @Expose
  private String status;
  public void setSortBy(String sortBy){
   this.sortBy=sortBy;
  }
  public String getSortBy(){
   return sortBy;
  }
  public void setSource(String source){
   this.source=source;
  }
  public String getSource(){
   return source;
  }
  public void setArticles(List<Articles> articles){
   this.articles=articles;
  }
  public List<Articles> getArticles(){
   return articles;
  }
  public void setStatus(String status){
   this.status=status;
  }
  public String getStatus(){
   return status;
  }
}