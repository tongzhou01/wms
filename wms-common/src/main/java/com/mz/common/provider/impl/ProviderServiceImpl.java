package com.mz.common.provider.impl;

import com.mz.common.provider.ProviderService;

public class ProviderServiceImpl implements ProviderService{
    @Override
    public String test() {
        return "I am provider";
    }
}
