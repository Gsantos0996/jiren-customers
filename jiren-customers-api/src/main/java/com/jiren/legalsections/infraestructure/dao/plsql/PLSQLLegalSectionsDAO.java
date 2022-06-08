package com.jiren.legalsections.infraestructure.dao.plsql;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.jiren.legalsections.domain.dao.LegalSectionsDAO;
import com.jiren.legalsections.domain.exeption.enumerator.LegalSectionsExceptionEnumerator;
import com.jiren.legalsections.domain.model.type.TypeEnumerator;
import com.jiren.legalsections.infraestructure.dao.jpa.JPALegalSections;
import com.jiren.legalsections.infraestructure.dao.jpa.JpaCustomer;
import com.jiren.legalsections.infraestructure.dao.jpa.JpaUser;
import com.jiren.legalsections.infraestructure.dao.repository.CustomersRepository;
import com.jiren.legalsections.infraestructure.dao.repository.LegalSectionsRepository;
import com.jiren.legalsections.infraestructure.dao.repository.UsersRepository;
import com.jiren.shared.exception.MPlusApiException;
import com.jiren.shared.guid.GUIDGenerator;
import com.jiren.shared.utils.DateUtils;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PLSQLLegalSectionsDAO implements LegalSectionsDAO {
	
	private final LegalSectionsRepository legalSectionsRepository;
	private final CustomersRepository customerRepository;
	private final UsersRepository userRepository;
	private final GUIDGenerator guidGenerator;
	
	private final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
	private final boolean ACTIVE = true;

	@Override
	public void create(String idUser, TypeEnumerator type) {		
		JpaUser jpaUser = validateUser(idUser);		
		Optional<JPALegalSections> legalFound = legalSectionsRepository.findByUserGuidAndType(jpaUser.getGuid(), type.getCode());

		if (legalFound.isPresent()) {
			String date = DateUtils.toString(Date.from(legalFound.get().getDate().toInstant(ZoneOffset.UTC)), DATE_PATTERN);			
			throw new MPlusApiException(LegalSectionsExceptionEnumerator.LEGAL_SECTIONS_DUPLICATE, type.getName(), date);
		}
		JPALegalSections jpaLegalSections = JPALegalSections.builder()
				.guid(guidGenerator.generateAlphaID())
				.user(jpaUser)
				.type(type.getCode())
				.date(LocalDateTime.now())
				.active(ACTIVE)
				.build();		
		legalSectionsRepository.save(jpaLegalSections);
	}
	
	@Override
	public Boolean verify(String idUser, TypeEnumerator type) {
		JpaUser jpaUser = validateUser(idUser);		
		return legalSectionsRepository.findByUserGuidAndType(jpaUser.getGuid(), type.getCode()).isPresent();		
	}

	private JpaUser validateUser(String idUser) {
		JpaUser jpaUser = userRepository.findByGuid(idUser);
		
		if (jpaUser == null)
			throw new MPlusApiException(LegalSectionsExceptionEnumerator.USER_NOT_FOUND, idUser);
		
		if (!jpaUser.getActive())
			throw new MPlusApiException(LegalSectionsExceptionEnumerator.USER_NOT_ACTIVE, idUser);
		
		String idCustomer = jpaUser.getCustomer().getGuid();
		JpaCustomer jpaCustomer = customerRepository.findById(idCustomer).orElseThrow(
					() -> new MPlusApiException(LegalSectionsExceptionEnumerator.CUSTOMER_NOT_FOUND, idCustomer)
				);
		if (!jpaCustomer.getActive())
			throw new MPlusApiException(LegalSectionsExceptionEnumerator.CUSTOMER_NOT_ACTIVE, jpaCustomer.getGuid());
		
		return jpaUser;
	}
}
