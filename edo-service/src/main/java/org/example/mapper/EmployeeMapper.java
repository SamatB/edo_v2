/**
 * Маппер для Employee
 */

package org.example.mapper;

import org.example.dto.EmployeeDto;
import org.example.entity.Employee;
import org.example.mapper.util.AbstractMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {AddressMapper.class, DeparmentMapper.class})
public interface EmployeeMapper extends AbstractMapper<Employee, EmployeeDto> {


}
