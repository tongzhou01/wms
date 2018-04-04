package com.mz.common.provider.impl;

import com.mz.common.provider.ProviderService;

public class ProviderServiceImplSec implements ProviderService{
    @Override
    public String test() {
        return "I am providerSec";
    }
}
