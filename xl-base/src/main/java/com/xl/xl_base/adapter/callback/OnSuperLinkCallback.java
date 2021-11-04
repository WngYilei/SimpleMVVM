package com.xl.xl_base.adapter.callback;

import androidx.annotation.NonNull;

/**
 * 超链接回调
 * <p>
 * Created by shengl on 2018/7/19.
 */
public interface OnSuperLinkCallback {

    void webLink(@NonNull String text);

    void phone(@NonNull String text);
}
