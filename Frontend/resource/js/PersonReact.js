class Persons extends React.Component {
    constructor(props) {
      super(props);
    }
  
    render() {
      return (
        <table class="table table-bordered table-striped table-hover">
          <thead>
            <tr>
              <th>#</th>            
              <th>ID number</th>
              <th>Fullname</th>
              <th>Gender</th>
              <th>Email</th>
              <th>Phone number</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {this.props.persons.map((person, index) => {
              return <Person person={person} index={index} />;
            })}
          </tbody>
        </table>
      );
    }
  }
  
  function Person(props) {
      let index = props.index +1;
      let name = props.person.name;
      let id = props.person.id;
      let phoneNumber = props.person.phoneNumber;
      let gender = props.person.gender;
      let email = props.person.email;
      return (
        <tr className = {index}>
          <td>{index}</td>
          <td className = "id">{id}</td>
          <td className = "name">{name}</td>  
          <td className = "gender">{gender}</td>    
          <td className = "email">{email}</td>
          <td className = "phone-number">{phoneNumber}</td>
          <td>
              <button className={"edit-action-btn " + index}>Edit</button>
          </td>
        </tr>
      );
  }