package com.bubble.sms.dao;

import com.bubble.sms.entity.SmsRecordEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsDao extends CrudRepository<SmsRecordEntity, String> {


}
