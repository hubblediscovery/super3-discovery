// Generated Feb 25, 2013 9:55:15 PM by Hibernate Tools 4.0.0

package com.hubble.store.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * HubbleAppData generated by hbm2java
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Entity
@Table(name = "HUBBLE_APP_DATA", catalog = "hubble")
public class HubbleAppData implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer hubbleAppId;
	private int appId;
	private String appBundleId;
	private Date appLastUpdatedDate;
	private Date appReleasedDate;
	private String appTitle;
	private String appDescription;
	private String appName;
	private String appDeveloperInfo;
	private String appType;
	private String appPrimaryCategory;
	private String appSecondaryCategory;
	private BigDecimal appUnitPrice;
	private String appUnitCurrency;
	private Set<HubbleEnrichedAppData> hubbleEnrichedAppDatas = new HashSet(0);

	public HubbleAppData() {
	}

	public HubbleAppData(int appId, String appBundleId, String appTitle) {
		this.appId = appId;
		this.appBundleId = appBundleId;
		this.appTitle = appTitle;
	}

	
	public HubbleAppData(int appId, String appBundleId,
			Date appLastUpdatedDate, Date appReleasedDate, String appTitle,
			String appDescription, String appName, String appDeveloperInfo,
			String appType, String appPrimaryCategory,
			String appSecondaryCategory, BigDecimal appUnitPrice,
			String appUnitCurrency, Set hubbleEnrichedAppDatas) {
		this.appId = appId;
		this.appBundleId = appBundleId;
		this.appLastUpdatedDate = appLastUpdatedDate;
		this.appReleasedDate = appReleasedDate;
		this.appTitle = appTitle;
		this.appDescription = appDescription;
		this.appName = appName;
		this.appDeveloperInfo = appDeveloperInfo;
		this.appType = appType;
		this.appPrimaryCategory = appPrimaryCategory;
		this.appSecondaryCategory = appSecondaryCategory;
		this.appUnitPrice = appUnitPrice;
		this.appUnitCurrency = appUnitCurrency;
		this.hubbleEnrichedAppDatas = hubbleEnrichedAppDatas;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "HUBBLE_APP_ID", unique = true, nullable = false)
	public Integer getHubbleAppId() {
		return this.hubbleAppId;
	}

	public void setHubbleAppId(Integer hubbleAppId) {
		this.hubbleAppId = hubbleAppId;
	}

	@Column(name = "APP_ID", nullable = false)
	public int getAppId() {
		return this.appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	@Column(name = "APP_BUNDLE_ID", nullable = false, length = 2000)
	public String getAppBundleId() {
		return this.appBundleId;
	}

	public void setAppBundleId(String appBundleId) {
		this.appBundleId = appBundleId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "APP_LAST_UPDATED_DATE", length = 0)
	public Date getAppLastUpdatedDate() {
		return this.appLastUpdatedDate;
	}

	public void setAppLastUpdatedDate(Date appLastUpdatedDate) {
		this.appLastUpdatedDate = appLastUpdatedDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "APP_RELEASED_DATE", length = 0)
	public Date getAppReleasedDate() {
		return this.appReleasedDate;
	}

	public void setAppReleasedDate(Date appReleasedDate) {
		this.appReleasedDate = appReleasedDate;
	}

	@Column(name = "APP_TITLE", nullable = false, length = 2000)
	public String getAppTitle() {
		return this.appTitle;
	}

	public void setAppTitle(String appTitle) {
		this.appTitle = appTitle;
	}

	@Column(name = "APP_DESCRIPTION", length = 65535)
	public String getAppDescription() {
		return this.appDescription;
	}

	public void setAppDescription(String appDescription) {
		this.appDescription = appDescription;
	}

	@Column(name = "APP_NAME", length = 2000)
	public String getAppName() {
		return this.appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	@Column(name = "APP_DEVELOPER_INFO", length = 2000)
	public String getAppDeveloperInfo() {
		return this.appDeveloperInfo;
	}

	public void setAppDeveloperInfo(String appDeveloperInfo) {
		this.appDeveloperInfo = appDeveloperInfo;
	}

	@Column(name = "APP_TYPE", length = 2000)
	public String getAppType() {
		return this.appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	@Column(name = "APP_PRIMARY_CATEGORY", length = 2000)
	public String getAppPrimaryCategory() {
		return this.appPrimaryCategory;
	}

	public void setAppPrimaryCategory(String appPrimaryCategory) {
		this.appPrimaryCategory = appPrimaryCategory;
	}

	@Column(name = "APP_SECONDARY_CATEGORY", length = 2000)
	public String getAppSecondaryCategory() {
		return this.appSecondaryCategory;
	}

	public void setAppSecondaryCategory(String appSecondaryCategory) {
		this.appSecondaryCategory = appSecondaryCategory;
	}

	@Column(name = "APP_UNIT_PRICE", precision = 10, scale = 4)
	public BigDecimal getAppUnitPrice() {
		return this.appUnitPrice;
	}

	public void setAppUnitPrice(BigDecimal appUnitPrice) {
		this.appUnitPrice = appUnitPrice;
	}

	@Column(name = "APP_UNIT_CURRENCY", length = 50)
	public String getAppUnitCurrency() {
		return this.appUnitCurrency;
	}

	public void setAppUnitCurrency(String appUnitCurrency) {
		this.appUnitCurrency = appUnitCurrency;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "hubbleAppData")
	public Set<HubbleEnrichedAppData> getHubbleEnrichedAppDatas() {
		return this.hubbleEnrichedAppDatas;
	}

	public void setHubbleEnrichedAppDatas(Set<HubbleEnrichedAppData> hubbleEnrichedAppDatas) {
		this.hubbleEnrichedAppDatas = hubbleEnrichedAppDatas;
	}

}