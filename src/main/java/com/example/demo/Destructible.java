package com.example.demo;

/**
 * Interface representing a destructible entity.
 */
public interface Destructible {

	/**
	 * Inflicts damage to the entity.
	 */
	void takeDamage();

	/**
	 * Destroys the entity.
	 */
	void destroy();
}