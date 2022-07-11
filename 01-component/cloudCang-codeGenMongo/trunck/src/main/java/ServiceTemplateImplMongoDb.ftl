package ${info.serviceImplPackage};

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


import com.cang.os.common.dao.BaseMongoDao;
import com.cang.os.common.service.BaseServiceImpl;



import ${info.daoPackage}.${info.daoName};
import ${info.modelPackage}.${info.modelName};
import ${info.servicePackage}.${info.modelName}Service;

@Service
public class ${info.modelName}ServiceImpl extends BaseServiceImpl<${info.modelName}> implements
		${info.modelName}Service {

	@Autowired
	${info.daoName} ${info.daoVarName};

	
	@Override
	public BaseMongoDao<${info.modelName}> getDao() {
		return ${info.daoVarName};
	}

	
	

}
