package cn.tosptream.data.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ReportConfig {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String className;
    private int status;

    protected ReportConfig() {}

    public ReportConfig(String className, int status) {
        this.className = className;
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, className='%s', status='%s']",
                id, className, status);
    }

    public int getId() {
        return id;
    }

    public String getClassName() {
        return className;
    }

    public int getStatus() {
        return status;
    }

}
