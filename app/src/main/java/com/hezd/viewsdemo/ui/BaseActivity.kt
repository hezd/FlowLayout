package com.hezd.viewsdemo.ui

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 *
 *@author hezd
 *Create on 2021/12/23 14:36
 */
abstract class BaseActivity<T :ViewDataBinding> :AppCompatActivity() {
    protected lateinit var binding:T
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,getLayoutId())
        initViews()
        initListeners()
        initData()
    }

    abstract fun getLayoutId():Int
    abstract fun initViews()
    abstract fun initListeners()
    abstract fun initData()
}