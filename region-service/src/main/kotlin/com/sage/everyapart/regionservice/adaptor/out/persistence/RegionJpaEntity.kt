package com.sage.everyapart.regionservice.adaptor.out.persistence

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "region")
class RegionJpaEntity {

    @Id
    @Column(name = "region_code")
    var regionCode:String

    @Column(name = "metropolitan_city_name")
    var metropolitanCityName:String

    @Column(name = "city_name")
    var cityName:String?

    @Column(name = "district_name")
    var districtName:String?

    @Column(name = "village_name")
    var villageName:String?

    @Column(name = "created_date")
    var createdDate:String

    @Column(name = "deleted_date")
    var deletedDate:String?

    @Column(name = "legal_region_code")
    var legalRegionCode:String?

    constructor(
        regionCode: String,
        metropolitanCityName: String,
        cityName: String,
        districtName: String?,
        villageName: String?,
        createdDate: String,
        deletedDate: String?,
        legalRegionCode: String?
    ) {
        this.regionCode = regionCode
        this.metropolitanCityName = metropolitanCityName
        this.cityName = cityName
        this.districtName = districtName
        this.villageName = villageName
        this.createdDate = createdDate
        this.deletedDate = deletedDate
        this.legalRegionCode = legalRegionCode
    }

}
