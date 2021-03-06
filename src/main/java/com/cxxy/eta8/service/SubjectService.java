package com.cxxy.eta8.service;

import com.cxxy.eta8.common.WebConfig;
import com.cxxy.eta8.db.DbConfig;
import com.cxxy.eta8.db.DbRecord;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Record;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目相关业务类
 *
 * @author LZH
 */
public class SubjectService {

    public static final SubjectService me = new SubjectService();

    private SubjectService() {

    }

    /**
     * 添加项目（包括负责人和参与者）
     */
    public boolean addSubject(Integer[] ids, Integer SubjectId) {
        final List<Record> records = new ArrayList<Record>();
        if(ids == null){
            Record record = new Record();
            record.set("SubjectId", SubjectId);
            record.set("CandidateId", 1).set("UserId", UserService.me.getCurrentUser().getInt("id"));
            records.add(record);
        }
        else {
            for (int i = 0; i <= ids.length; i++) {
                Record record = new Record();
                record.set("SubjectId", SubjectId);
                if (i == 0) {
                    record.set("CandidateId", 1).set("UserId", UserService.me.getCurrentUser().getInt("id"));
                } else {
                    record.set("CandidateId", 2).set("UserId", ids[i - 1]);
                }
                records.add(record);
            }
        }
        return Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                Db.batchSave(DbConfig.T_SUBJECT_LINK_TEACHER, records, records.size());
                return true;
            }
        });
    }

    /**
     * 删除项目（根据传入的levelId删除另一个分表）
     */
    public boolean delSubject(Integer[] ids, Integer SubjectId) {
        final List<Record> records = new ArrayList<Record>();
        for (int i = 0; i < ids.length; i++) {
            Record record = new Record();
            record.set("SubjectId", SubjectId).set("UserId", ids[i]);
            records.add(record);
        }
        return Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                boolean success = true;
                for (int i = 0; i < records.size(); i++) {
                    List<Record> ids = new DbRecord(DbConfig.T_USER_SUBJECT)
                            .whereEqualTo("SubjectNum", records.get(i).getStr("SubjectNum")).query();
                    for (int j = 0; j < ids.size(); j++) {
                        Integer levelId = ids.get(j).getInt("LevelId");
                        if (levelId == WebConfig.SUBJECT_TYPE_SPONSORED) {
                            List<Record> levelids = new DbRecord(DbConfig.T_SUBJECT_SPONSORED).whereEqualTo("SubjectId", SubjectId).query();
                            if (!Db.deleteById(DbConfig.T_USER_SUBJECT, ids.get(j).getInt("id")) && !Db.deleteById(DbConfig.T_SUBJECT_SPONSORED, levelids.get(j).getInt("id"))) {
                                success = false;
                            }
                        }else if (levelId == WebConfig.SUBJECT_TYPE_HORIZON) {
                            List<Record> levelids = new DbRecord(DbConfig.T_SUBJECT_HORIZON).whereEqualTo("SubjectId", SubjectId).query();
                            if (!Db.deleteById(DbConfig.T_USER_SUBJECT, ids.get(j).getInt("id")) && !Db.deleteById(DbConfig.T_SUBJECT_HORIZON, levelids.get(j).getInt("id"))) {
                                success = false;
                            }
                        }else if(levelId == WebConfig.SUBJECT_TYPE_SCHOOL) {
                            List<Record> levelids = new DbRecord(DbConfig.T_SUBJECT_SCHOOL).whereEqualTo("SubjectId", SubjectId).query();
                            if (!Db.deleteById(DbConfig.T_USER_SUBJECT, ids.get(j).getInt("id")) && !Db.deleteById(DbConfig.T_SUBJECT_SCHOOL, levelids.get(j).getInt("id"))) {
                                success = false;
                            }
                        }
                    }
                }
                return success;
            }
        });
    }

    /**
     * 项目插入成果ID
     */
    public boolean setSubjectPaper(Integer SubjectId, Integer PaperId) {
        final List<Record> records = new ArrayList<Record>();
        List<Record> ids = new DbRecord(DbConfig.T_SUBJECT_LINK_PAPER)
                .whereEqualTo("PaperId", PaperId).query();
        if(ids.isEmpty()) {
            Record record = new Record();
            record.set("SubjectId", SubjectId).set("PaperId", PaperId);
            records.add(record);
            return Db.tx(new IAtom() {
                public boolean run() throws SQLException {
                    Db.batchSave(DbConfig.T_SUBJECT_LINK_PAPER, records, records.size());
                    return true;
                }
            });
        }else {
            return false;
        }
    }


    /**
     * 添加项目（包括负责人和参与者）
     */
    public boolean addPaper(Integer[] ids, Integer PaperId) {
        final List<Record> records = new ArrayList<Record>();
        if(ids == null){
            Record record = new Record();
            record.set("PaperId", PaperId);
            record.set("CandidateId", 1).set("UserId", UserService.me.getCurrentUser().getInt("id"));
            records.add(record);
        }
        else {
            for (int i = 0; i <= ids.length; i++) {
                Record record = new Record();
                record.set("PaperId", PaperId);
                if (i == 0) {
                    record.set("CandidateId", 1).set("UserId", UserService.me.getCurrentUser().getInt("id"));
                } else {
                    record.set("CandidateId", 2).set("UserId", ids[i - 1]);
                }
                records.add(record);
            }
        }
        return Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                Db.batchSave(DbConfig.T_PAPER_LINK_TEACHER, records, records.size());
                return true;
            }
        });
    }

    /**
     * 删除项目（包括负责人和参与者）
     */
    public boolean delPaper(Integer[] ids, Integer PaperId) {
        final List<Record> records = new ArrayList<Record>();
        for (int i = 0; i < ids.length; i++) {
            Record record = new Record();
            record.set("PaperId", PaperId).set("UserId", ids[i]);
            records.add(record);
        }
        return Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                boolean success = true;
                for (int i = 0; i < records.size(); i++) {
                    List<Record> ids = new DbRecord(DbConfig.T_USER_PAPER)
                            .whereEqualTo("SubjectNum", records.get(i).getStr("SubjectNum")).query();
                    for (int j = 0; j < ids.size(); j++) {
                        if (!Db.deleteById(DbConfig.T_USER_PAPER, ids.get(j).getInt("id"))) {
                            success = false;
                        }
                    }
                }
                return success;
            }
        });
    }

    /**
     * 添加项目（包括负责人和参与者）
     */
    public boolean updateSubjectPaper(Integer PaperId, Integer SubjectId) {
        final List<Record> records = new ArrayList<Record>();
                Record record = new Record();
                record.set("SubjectId", SubjectId);
                record.set("PaperId", PaperId);
                records.add(record);

        return Db.tx(new IAtom() {
            public boolean run() throws SQLException {
                Db.batchSave(DbConfig.T_SUBJECT_LINK_PAPER, records, records.size());
                return true;
            }
        });
    }

}
