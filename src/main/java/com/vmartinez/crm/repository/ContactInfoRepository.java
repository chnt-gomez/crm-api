package com.vmartinez.crm.repository;

import com.vmartinez.crm.entity.ContactInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactInfoRepository  extends JpaRepository<ContactInfo, Long> {
}
