package com.example.david_k.oneStopClick.ModelLayers.Enums;

/**
 * Created by David_K on 11/10/2017.
 */

import com.example.david_k.oneStopClick.R;

public enum NavBarItem {
    PRODUCT(R.id.nav_product),
    CHART(R.id.nav_chart),
    ADMIN(R.id.nav_chart),
    ABOUT(R.id.nav_chart);

    private int itemId;
    NavBarItem(int id) {
        this.itemId = id;
    }

    public int getItemId() {
        return itemId;
    }

    public static NavBarItem fromViewId(int viewId){
        for (NavBarItem navBarItem : NavBarItem.values()) {
            if (navBarItem.getItemId() == viewId){
                return navBarItem;
            }
        }
        throw new IllegalStateException("Cannot find viewType");
    }
}
