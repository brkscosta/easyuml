package org.uml.model.components;

import java.lang.reflect.Modifier;
import org.uml.model.members.Field;
import org.uml.model.members.Constructor;
import java.util.LinkedHashSet;
import org.uml.model.members.MemberBase;
import org.uml.model.members.Method;

/**
 * Class component in a class diagram.
 *
 * @author Uros
 * @see InterfaceComponent
 * @see EnumComponent
 */
public class ClassComponent extends ComponentBase {

    /**
     * Set of fields this class contains.
     */
    private LinkedHashSet<Field> fields;

    /**
     * Set of constructors this class contains.
     */
    private LinkedHashSet<Constructor> constructors;

    /**
     * Set of method this class contains.
     */
    private LinkedHashSet<Method> methods;

    /**
     * Default constructor. Sets name to default value.
     * <p>
     * Used when deserializing or adding a new component from palette or popup menu.
     */
    public ClassComponent() {
        this("UntitledClass");
    }

    /**
     * Constructor with parameters which calls its super constructor.
     * Sets the name of the class.
     * Instantiates fields, methods and constructors collections.
     *
     * @param name to be set
     */
    // Used when reverse engineering
    public ClassComponent(String name) {
        super(name);
        fields = new LinkedHashSet<>();
        constructors = new LinkedHashSet<>();
        methods = new LinkedHashSet<>();
    }

    /**
     * Returns the collection of fields that this class contains.
     *
     * @return HashSet of fields contained
     */
    public LinkedHashSet<Field> getFields() {
        return fields;
    }

    /**
     * Returns the collection of methods that this class contains.
     *
     * @return HashSet of methods contained
     */
    public LinkedHashSet<Method> getMethods() {
        return methods;
    }

    /**
     * Returns the collection of constructors that this class contains.
     *
     * @return HashSet of constructors contained
     */
    public LinkedHashSet<Constructor> getConstructors() {
        return constructors;
    }

    /**
     * Adds the given field to the class.
     * Sets the declaring component of the field to be this class.
     *
     * @param field to add
     */
    public void addField(Field field) {
        // Adds to ComponentBase container (components collection - set of all members).
        addComponentToContainter(field);
        field.setDeclaringComponent(this);
        // Adds to ClassComponent's fields (set of fields only).
        fields.add(field);
    }

    /**
     * Adds the given methode to the class.
     * Sets the declaring component of the method to be this class.
     *
     * @param method to add
     */
    public void addMethod(Method method) {
        addComponentToContainter(method);
        method.setDeclaringComponent(this);
        methods.add(method);
    }

    /**
     * Adds the given constructor to the class.
     * Sets the declaring component of the constructor to be this class.
     *
     * @param constructor to add
     */
    public void addConstructor(Constructor constructor) {
        addComponentToContainter(constructor);
        constructor.setDeclaringComponent(this);
        constructors.add(constructor);
    }

    /**
     * Returns true if abstract modifier bit is set, false if not.
     *
     * @return true if class is abstract
     */
    public boolean isAbstract() {
        return Modifier.isAbstract(modifiers);
    }

    /**
     * Sets the abstract modifier to true or false. Fires "isAbstract" property change event.
     *
     * @param isAbstract true if the class is abstract, false if not
     */
    public void setAbstract(boolean isAbstract) {
        int oldModifiers = modifiers;
        if (isAbstract) {
            addModifier(Modifier.ABSTRACT);
        } else {
            removeModifier(Modifier.ABSTRACT);
        }
        pcs.firePropertyChange("isAbstract", Modifier.isAbstract(oldModifiers), isAbstract());
    }

    /**
     * Returns true if static modifier bit is set, false if not.
     *
     * @return true if class is static
     */
    public boolean isStatic() {
        return Modifier.isStatic(modifiers);
    }

    /**
     * Sets the static modifier to true or false. Fires "isStatic" property change event.
     *
     * @param isStatic true if the class is static, false if not
     */
    public void setStatic(boolean isStatic) {
        int oldModifiers = modifiers;
        if (isStatic) {
            addModifier(Modifier.STATIC);
        } else {
            removeModifier(Modifier.STATIC);
        }
        pcs.firePropertyChange("isStatic", Modifier.isStatic(oldModifiers), isStatic());
    }

    /**
     * Returns true if final modifier bit is set, false if not.
     *
     * @return true if class is final
     */
    public boolean isFinal() {
        return Modifier.isFinal(modifiers);
    }

    /**
     * Sets the final modifier to true or false. Fires "isFinal" property change event.
     *
     * @param isFinal true if the class is final, false if not
     */
    public void setFinal(boolean isFinal) {
        int oldModifiers = modifiers;
        if (isFinal) {
            addModifier(Modifier.FINAL);
        } else {
            removeModifier(Modifier.FINAL);
        }
        pcs.firePropertyChange("isFinal", Modifier.isFinal(oldModifiers), isFinal());
    }

    /**
     * Removes the member from the associated collection.
     *
     * @param member to be removed
     */
    @Override
    public void removeMember(MemberBase member) {
        removeComponentFromContainer(member);
        if (member instanceof Field) fields.remove((Field) member);
        else if (member instanceof Method) methods.remove((Method) member);
        else if (member instanceof Constructor) constructors.remove((Constructor) member);
//        else throw new RuntimeException("Removing unsupported member: " + member.toString());
    }
}
