class Vehicles extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <table class="table table-bordered table-striped table-hover">
        <thead>
          <tr>
            <th>#</th>
            <th>License plate</th>
            <th>Owner 's fullname</th>
            <th>Brand</th>
            <th>Model code</th>     
            <th>Kind</th>      
            <th>Color</th>
          </tr>
        </thead>
        <tbody>
          {this.props.vehicles.map((vehicle, index) => {
            return <Vehicle vehicle={vehicle} index={index} />;
          })}
        </tbody>
      </table>
    );
  }
}

function Vehicle(props) {
    let index = props.index +1;
    let numberPlate = props.vehicle.numberPlate;
    let brand = props.vehicle.carManufacturer;
    let modelCode = props.vehicle.nameVehicle;
    let ownerName = props.vehicle.person.name;
    let color = props.vehicle.color;
    return (
      <tr>
        <td>{index}</td>
        <td>{numberPlate}</td>
        <td>{ownerName}</td>
        <td>{brand}</td>
        <td>{modelCode}</td>  
        <td>Car</td>     
        <td>{color}</td>
      </tr>
    );
  }