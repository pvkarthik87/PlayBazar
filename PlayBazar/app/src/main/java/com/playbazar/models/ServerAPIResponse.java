package com.playbazar.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class ServerAPIResponse {

	@SerializedName("page")
	@Expose
	private Integer page;
	@SerializedName("pageSize")
	@Expose
	private Integer pageSize;
	@SerializedName("totalPageCount")
	@Expose
	private Integer totalPageCount;
	@SerializedName("wkda")
	@Expose
	private Map<String, String> mBrandsMap;

	/**
	 *
	 * @return
	 * The page
	 */
	public Integer getPage() {
		return page;
	}

	/**
	 *
	 * @param page
	 * The page
	 */
	public void setPage(Integer page) {
		this.page = page;
	}

	/**
	 *
	 * @return
	 * The pageSize
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 *
	 * @param pageSize
	 * The pageSize
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 *
	 * @return
	 * The totalPageCount
	 */
	public Integer getTotalPageCount() {
		return totalPageCount;
	}

	/**
	 *
	 * @param totalPageCount
	 * The totalPageCount
	 */
	public void setTotalPageCount(Integer totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	/**
	 *
	 * @return
	 * The mBrandsMap
	 */
	public Map<String, String> getWkda() {
		return mBrandsMap;
	}

	/**
	 *
	 * @param wkda
	 * The mBrandsMap
	 */
	public void setWkda(Map<String, String> wkda) {
		this.mBrandsMap = wkda;
	}

}

