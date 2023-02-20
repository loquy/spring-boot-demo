package script;

import java.security.Permission;

/**
 * @author loquy
 */
public class ScriptSecurityManager extends SecurityManager {

    private static boolean destroy = false;
    private static long threadId;

    public static void initPermission(long threadId) {
        SecurityManager originalSecurityManager = System.getSecurityManager();
        if (originalSecurityManager == null) {
            SecurityManager sm = new ScriptSecurityManager();
            System.setSecurityManager(sm);
        }
        ScriptSecurityManager.threadId = threadId;
    }

    public static void destroyPermission() {
        ScriptSecurityManager.destroy = true;
        System.setSecurityManager(null);
    }

    private void check(Permission perm) throws ScriptException {
        long threadId = Thread.currentThread().getId();
        if (threadId == ScriptSecurityManager.threadId) {
            String name = perm.getName();
            String actions = perm.getActions();
            if (perm instanceof RuntimePermission) {
                String setSecurityManager = "setSecurityManager";
                if (name.contains(setSecurityManager) && !destroy) {
                    throw new SecurityException("不允许设置安全管理器！");
                }
                checkPerm(name, "exitVM", "不允许调用exit方法！");
                checkPerm(name, "loadLibrary", "不允许链接库！");
                checkPerm(name, "createClassLoader", "不允许创建类加载器！");
                checkPerm(name, "getClassLoader", "不允许获取类加载器！");
                checkPerm(name, "writeFileDescriptor", "不允许写入文件描述符！");
                checkPerm(name, "queuePrintJob", "不允许调用线程发起打印作业请求！");
                checkPerm(name, "setContextClassLoader", "不允许线程使用的上下文类装入器的设置！");
                checkPerm(name, "enableContextClassLoaderOverride", "不允许线程上下文类装入器方法的子类实现！");
                checkPerm(name, "closeClassLoader", "不允许关闭类加载器！");
                checkPerm(name, "createSecurityManager", "不允许创建一个新的安全管理器！");
                checkPerm(name, "shutdownHooks", "不允许注册和取消虚拟机关机钩子！");
                checkPerm(name, "setFactory", "不允许设置ServerSocket或socket使用的套接字工厂，或URL使用的流处理程序工厂！");
                checkPerm(name, "setIO", "不允许System.out、 System.in 和 System.err 的设置！");
                checkPerm(name, "modifyThread", "不允许线程的修改！");
                checkPerm(name, "defineClassInPackage", "不允许在参数指定的包中定义类！");
                checkPerm(name, "modifyThread", "不允许线程的修改！");
                checkPerm(name, "stopThread", "不允许通过调用Thread stop方法停止线程！");
                checkPerm(name, "modifyThreadGroup", "不允许修改线程组！");
                checkPerm(name, "getProtectionDomain", "不允许获取特定代码源的策略信息！");
                checkPerm(name, "getFileSystemAttributes", "不允许文件系统属性的检索！");
                checkPerm(name, "loadLibrary", "不允许指定库的动态链接！");
                checkPerm(name, "accessClassInPackage", "不允许通过类装入器的loadClass方法访问指定的包！");
                checkPerm(name, "defineClassInPackage", "不允许通过类装入器的defineClass方法定义指定包中的类！");
                checkPerm(name, "accessDeclaredMembers", "不允许对类的已声明成员的访问！");
                checkPerm(name, "queuePrintJob", "不允许打印作业请求的启动！");
                checkPerm(name, "getStackTrace", "不允许获取另一个线程的堆栈跟踪信息！");
                checkPerm(name, "setDefaultUncaughtExceptionHandler", "不允许设置当线程因未捕获异常而突然终止时使用的默认处理程序！");
                checkPerm(name, "preferences", "不允许允许在Preferences持久备份存储中检索或更新操作！");
                checkPerm(name, "usePolicy", "不允许授予禁用Java插件的默认安全提示行为！");
            }
            if (perm instanceof java.io.FilePermission) {
                checkPerm(actions, "execute", "不允许调用exec方法！");
                checkPerm(actions, "write", "不允许写入文件！");
                checkPerm(actions, "delete", "不允许删除文件！");
            }
            if (perm instanceof java.net.SocketPermission) {
                checkPerm(name, "resolve,connect", "不允许打开到指定主机和端口号的套接字连接！");
                checkPerm(name, "listen", "不允许在指定的本地端口号上等待连接请求！");
                checkPerm(name, "connect,accept", "不允许接受来自指定主机和端口号的套接字连接！");
            }
            if (perm instanceof java.util.PropertyPermission) {
                checkPerm(name, "read,write", "不允许访问或修改系统属性！");
            }
            if (perm instanceof java.security.SecurityPermission) {
                checkPerm(name,"createAccessControlContext,getDomainCombiner,getPolicy,setPolicy,createPolicy,getProperty," +
                                "setProperty,insertProvider,removeProvider,clearProviderProperties,putProviderProperty,removeProviderProperty",
                        "不允许具有指定权限目标名称的权限！");
            }
        }
    }

    @Override
    public void checkPermission(Permission perm) throws ScriptException {
        check(perm);

    }

    @Override
    public void checkPermission(Permission perm, Object context) throws ScriptException {
        check(perm);
    }

    private void checkPerm(String perm, String checks, String msg) throws ScriptException {
        String[] check = checks.split(",");
        for (String checkPerm : check) {
            if (perm.contains(checkPerm)) {
                throw new ScriptException(msg);
            }
        }
    }
}
