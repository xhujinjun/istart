package com.istart.framework.domain.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
/**
 * 所有实体的基类
 * 注意事项或特色：
 * 	1.有一个无参构造函数和一个id参数的构造函数   每个实体都应该有一个无参的构造函数（建议写出来），要不然Hibernate执行sql转换为实体时会调用Class.newInstance()方法，会抛出Instantiation异常
 * 	2.实现了Serializabel接口
 *  3.Id为字符串类型，系统根据UUID自动生成
 * @author 进军
 *
 */
@MappedSuperclass
public abstract class BaseEntity<PK extends Serializable>implements Serializable{

	private static final long serialVersionUID = -1759578866402133231L;
	/**
	 * 数据ID:一条数据的唯一标识
	 */
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid")
	@Column(name="ID")
	private PK id;

//	@Version
//    private int version;
	
	public BaseEntity() {
	}

	public BaseEntity(final PK id) {
		super();
		this.id = id;
	}
	
	public PK getId() {
		return id;
	}
	/**
	 * 不提供对外修改ID属性的方法
	 * @param id
	 */
	public void setId(PK id) {
		this.id = id;
	}
//	public int getVersion() {
//		return version;
//	}
//
//	public void setVersion(int version) {
//		this.version = version;
//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseEntity<?> other = (BaseEntity<?>) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
