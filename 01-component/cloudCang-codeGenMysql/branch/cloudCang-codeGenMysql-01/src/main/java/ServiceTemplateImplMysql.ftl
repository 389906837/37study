package ${info.serviceImplPackage};

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import ${info.daoPackage}.${info.daoName};
import ${info.modelPackage}.${info.modelName};
import ${info.servicePackage}.${info.modelName}Service;

@Service
public class ${info.modelName}ServiceImpl extends GenericServiceImpl<${info.modelName}, String> implements
		${info.modelName}Service {

	@Autowired
	${info.daoName} ${info.daoVarName};

	
	@Override
	public GenericDao<${info.modelName}, String> getDao() {
		return ${info.daoVarName};
	}

	
	

}
