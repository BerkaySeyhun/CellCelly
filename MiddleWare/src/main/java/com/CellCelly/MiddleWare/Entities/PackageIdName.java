
package com.CellCelly.MiddleWare.Entities;


public class PackageIdName {
    
    private long packageId;
    private String packageName;

    public PackageIdName(long packageId, String packageName) {
        this.packageId = packageId;
        this.packageName = packageName;
    }

    public long getPackageId() {
        return packageId;
    }

    public void setPackageId(long packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public String toString() {
        return "PackageIdName{" + "packageId=" + packageId + ", packageName=" + packageName + '}';
    }
    
    
    
    
}
