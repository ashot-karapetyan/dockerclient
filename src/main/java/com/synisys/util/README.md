**idm-docker-database**

**Example how to integrate api in your test**
    
    @RunWith(SpringJUnit4ClassRunner.class)
    @ContextConfiguration(locations = "classpath:test-service-config.xml")
    @DirtiesContext
    public class MessageServiceTest {
    
    private static DockerDBContainerManager dockerDBContainerManager = new DockerMySQLContainerManager();

	private static DatabaseCreatedListener databaseCreatedListener = new MigrateDataBaseOnCreated();

	@ClassRule
	public static CreateDataBaseClassRule createDbClassRule = new CreateDataBaseClassRule(dockerDBContainerManager,
			"message_language_db", Collections.singletonList(databaseCreatedListener),
			"src/test/resources/db/test-data/test-data.sql");

	@Rule
	public CreateDataBaseMethodRule createDataBaseMethodRule;

	@Autowired
	private void setDataBaseInstanceListener(DataBaseInstanceListener dataBaseInstanceListener) {
		this.createDataBaseMethodRule = new CreateDataBaseMethodRule(dataBaseInstanceListener, createDbClassRule);
	}
	
	@Autowired
    private MessageService messageService;
    
    /**
     * Expect IllegalArgumentException when call with wrong application identifier
     */
    @Test
    public void getMessagesEmptyTest() {
    	//some code
    }
    	
    /**
     * When some test is corruptes the database data you can restore state 
     * using RequireNewCopy anotation and add scripts which run after test
     */
    @RequireNewCopy({"src/test/resources/db/test-data/V1__init.sql"})
    @Test
    public void testWichCorruptedData() {
         //some code 
    }

    }	
    
**Docker client Properties**    
    DOCKER_HOST=tcp://sis2s088:2375  
    DOCKER_TLS_VERIFY
    DOCKER_CERT_PATH