package cn.tosptream.data.service;

import cn.tosptream.data.dao.ReportConfig;
import cn.tosptream.data.dao.ReportConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebService {
    @Autowired
    private ReportConfigRepository repository;

    public List<ReportConfig> getAll() {
     return (List<ReportConfig>) repository.findAll();
    }
}
