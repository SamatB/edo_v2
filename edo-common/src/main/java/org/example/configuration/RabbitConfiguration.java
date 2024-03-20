package org.example.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс конфигурации RabbitMQ
 */
@Configuration
@EnableRabbit
public class RabbitConfiguration {
    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;

    @Value("${spring.rabbitmq.template.default-receive-queue}")
    private String queue;

    /**
     * Очередь и RoutingKey для операции сохранения в БД работника
     */
    final public static String SAVE_EMPLOYEE_ROUTING_KEY = "saveEmployee";

    @Value("${spring.rabbitmq.template.routing-key}")
    private String routingKey;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.template.reply-timeout}")
    private Integer replyTimeout;
    public static final String SAVE_DEPARTMENT_ROUTING_KEY = "save_department";
    public static final String EMPLOYEE_DTO_ID = "employeeDtoId";

    /**
     * Бин, отвечающий за соединение с сервером RabbitMQ
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setHost(host);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    /**
     * Бин инициализации/настройки RabbitMQ
     */
    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    /**
     * Бин для отправки сообщений в очередь
     */
    @Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setDefaultReceiveQueue(queue);
        rabbitTemplate.setReplyAddress(queue);
        rabbitTemplate.setReplyTimeout(replyTimeout);
        rabbitTemplate.setUseDirectReplyToContainer(false);
        return rabbitTemplate;
    }

    /**
     * Бин очереди
     */
    @Bean
    public Queue queue() {
        return new Queue(queue);
    }

    /**
     * Бин очереди для операции сохранения работника в БД
     */
    @Bean
    public Queue saveEmployeeQueue() {
        return new Queue(SAVE_EMPLOYEE_ROUTING_KEY);
    }

    /**
     * Бин очереди для DepartmentDto
     */
    @Bean
    public Queue departmentQueue() {
        return new Queue(SAVE_DEPARTMENT_ROUTING_KEY);
    }


    /**
     * Бин очереди для Коллекции ID EmployeeDTO
     */
    @Bean
    public Queue employeeDtoId() {return new Queue(EMPLOYEE_DTO_ID);}


    /**
     * Бин обменника типа direct
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("directExchange");
    }

    /**
     * Бин обменника типа fanout
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("commonExchange");
    }

    /**
     * Бин связи очереди с обменником direct
     */
    @Bean
    public Binding directBinding() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(routingKey);
    }

    /**
     * Бин связи очереди department с обменником direct
     */
    @Bean
    public Binding directBindingDepartment() {
        return BindingBuilder.bind(departmentQueue()).to(directExchange()).with(SAVE_DEPARTMENT_ROUTING_KEY);
    }

    /**
     * Бин связи очереди с обменником fanout
     */
    @Bean
    public Binding fanoutBinding() {
        return BindingBuilder.bind(queue()).to(fanoutExchange());
    }

    /**
     * Бины связи очереди на сохранение работника с обменником direct
     */
    @Bean
    public Binding directBindingEmployee() {
        return BindingBuilder.bind(saveEmployeeQueue()).to(directExchange()).with(SAVE_EMPLOYEE_ROUTING_KEY);
    }
    /**
     * Бины связи очереди на сохранение работника с обменником fanout
     */
    @Bean
    public Binding fanoutBindingEmployee() {
        return BindingBuilder.bind(saveEmployeeQueue()).to(fanoutExchange());
    }
    /**
     * Бины связи очереди на сохранение департамента с обменником fanout
     */
    @Bean
    public Binding fanoutBindingDepartment() {
        return BindingBuilder.bind(departmentQueue()).to(fanoutExchange()); }

    /**
     * Очередь и ключ для отправки листа согласования
     */
    public static final String SEND_AGREEMENT_LIST_ROUTING_KEY = "sendAgreementList";
    @Bean
    public Queue sendAgreementListQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-max-priority", 255);
        return new Queue(SEND_AGREEMENT_LIST_ROUTING_KEY, true, false, false, args);
    }
    @Bean
    public Binding sendAgreementListBinding() {
        return BindingBuilder.bind(sendAgreementListQueue()).to(directExchange()).with(SEND_AGREEMENT_LIST_ROUTING_KEY);
    }

}
