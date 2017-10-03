package com.death.paper.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Awesome Pojo Generator
 * */
public class Articles{
  @SerializedName("publishedAt")
  @Expose
  private String publishedAt;
  @SerializedName("author")
  @Expose
  private String author;
  @SerializedName("urlToImage")
  @Expose
  private String urlToImage;
  @SerializedName("description")
  @Expose
  private String description;
  @SerializedName("title")
  @Expose
  private String title;
  @SerializedName("url")
  @Expose
  private String url;
  public void setPublishedAt(String publishedAt){
   this.publishedAt=publishedAt;
  }
  public String getPublishedAt(){
   return publishedAt;
  }
  public void setAuthor(String author){
   this.author=author;
  }
  public String getAuthor(){
   return author;
  }
  public void setUrlToImage(String urlToImage){
   this.urlToImage=urlToImage;
  }
  public String getUrlToImage(){
   return urlToImage;
  }
  public void setDescription(String description){
   this.description=description;
  }
  public String getDescription(){
   return description;
  }
  public void setTitle(String title){
   this.title=title;
  }
  public String getTitle(){
   return title;
  }
  public void setUrl(String url){
   this.url=url;
  }
  public String getUrl(){
   return url;
  }
}