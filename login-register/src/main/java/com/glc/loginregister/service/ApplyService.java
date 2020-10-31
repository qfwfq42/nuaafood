package com.glc.loginregister.service;

import com.glc.loginregister.mapper.ApplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplyService {
    @Autowired
    private ApplyMapper applyMapper;

}
