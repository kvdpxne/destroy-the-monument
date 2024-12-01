package me.kvdpxne.dtm.shared.reflection

import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.util.WeakHashMap
import org.bukkit.Bukkit

object Reflection {

  private val classes: MutableMap<String, Class<*>> = WeakHashMap(32)
  private val fields: MutableMap<String, FieldAccessor> = WeakHashMap()
  private val constructors: MutableMap<String, ConstructorInvoker> = WeakHashMap()
  private val methods: MutableMap<String, MethodInvoker> = WeakHashMap()

  val MINECRAFT_PACKAGE: String

  val BUKKIT_PACKAGE: String
  val CRAFT_BUKKIT_PACKAGE: String

  init {
    val versionPackageName: String = Bukkit.getServer().javaClass
      .`package`.name.split(".")[3]

    BUKKIT_PACKAGE = "org.bukkit"
    CRAFT_BUKKIT_PACKAGE = "$BUKKIT_PACKAGE.craftbukkit.$versionPackageName"
    MINECRAFT_PACKAGE = "net.minecraft.server.$versionPackageName"
  }

  fun getClass(path: String): Class<*> {
    val cachedClass: Class<*>? = this.classes[path]

    if (null != cachedClass) {
      return cachedClass
    }

    val foundClass: Class<*> = Class.forName(path)
    this.classes[path] = foundClass

    return foundClass
  }

  fun getBukkitClass(path: String): Class<*> {
    return this.getClass("$BUKKIT_PACKAGE.$path")
  }

  fun getCraftBukkitClass(path: String): Class<*> {
    return Class.forName("$CRAFT_BUKKIT_PACKAGE.$path")
  }

  fun getMinecraftClass(path: String): Class<*> {
    return this.getClass("$MINECRAFT_PACKAGE.$path")
  }

  fun getField(
    clazz: Class<*>,
    fieldName: String,
    fieldType: Class<*>? = null
  ): FieldAccessor {
    require(fieldName.isNotBlank()) {
      ""
    }

    val normalizedKey: String = "${clazz.name}.$fieldName".intern()
    val cachedFieldAccessor: FieldAccessor? = this.fields[normalizedKey]

    if (null != cachedFieldAccessor) {
      return cachedFieldAccessor
    }

    for (field: Field in clazz.declaredFields) {
      if (fieldName != field.name || (null != fieldType && !fieldType.isAssignableFrom(field.type))) {
        continue
      }

      if (!field.isAccessible) {
        field.isAccessible = true
      }

      val fieldAccessor: FieldAccessor = object : FieldAccessor {

        override fun get(target: Any?): Any? {
          return field.get(target)
        }

        override fun set(target: Any, value: Any): Any {
          return field.set(target, value)
        }

        override fun has(target: Any): Boolean {
          return field.declaringClass.isAssignableFrom(target.javaClass)
        }
      }

      this.fields[normalizedKey] = fieldAccessor
      return fieldAccessor
    }

    throw IllegalStateException("")
  }

  fun getMethod(
    clazz: Class<*>,
    methodName: String,
    returnType: Class<*>? = null,
    parameterTypes: Array<Class<*>>? = null
  ): MethodInvoker {
    require(methodName.isNotBlank()) {
      ""
    }

    val normalizedKey: String = "${clazz.name}.$methodName".intern()
    val cachedMethodInvoker: MethodInvoker? = this.methods[normalizedKey]

    if (null != cachedMethodInvoker) {
      return cachedMethodInvoker
    }

    for (method: Method in clazz.declaredMethods) {
      if (
        methodName != method.name ||
        (null != returnType && returnType != method.returnType) ||
        (null != parameterTypes && !parameterTypes.contentEquals(method.parameterTypes))
      ) {
        continue
      }

      if (!method.isAccessible) {
        method.isAccessible = true
      }

      val methodInvoker: MethodInvoker = object : MethodInvoker {

        override fun invoke(target: Any?, vararg parameters: Any): Any? {
          return method.invoke(target, *parameters)
        }
      }

      this.methods[normalizedKey] = methodInvoker
      return methodInvoker
    }

    throw IllegalStateException("")
  }

  fun getConstructor(clazz: Class<*>, vararg parameterTypes: Class<*>): ConstructorInvoker {


    for (constructor: Constructor<*> in clazz.declaredConstructors) {
      if (!parameterTypes.contentEquals(constructor.parameterTypes)) {
        continue
      }

      constructor.toString()

      if (!constructor.isAccessible) {
        constructor.isAccessible = true
      }

      val constructorInvoker: ConstructorInvoker = object : ConstructorInvoker {

        override fun invoke(vararg parameters: Any?): Any {
          return constructor.newInstance(*parameters)
        }
      }

      return constructorInvoker
    }

    throw IllegalStateException()
  }

  fun getConstructor2(
    clazz: Class<*>, parameterTypes: Array<Class<*>>
  ): ConstructorInvoker {
    return this.getConstructor(clazz, *parameterTypes)
  }
}