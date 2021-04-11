class Paging extends React.Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <ul className="pagination">
        {this.props.pages.map((page) => {
          return (
            <Page
              page={page}
              currentPage={this.props.currentPage}
              amountPages={this.props.amountPages}
            />
          );
        })}
      </ul>
    );
  }
}

function Page(props) {
  let page = props.page;
  let classNameOfPageLink = 'page-link';
  let href ;
  if (page == 0) {
    page = props.currentPage - 1;
    if (props.currentPage == 1) {
      page = 1;
    }
    href = "#" + page;
    return (
      <li className="page-item">
        <a className={classNameOfPageLink}  href={href}>
          Previous
        </a>
      </li>
    );
  } else if (page > props.amountPages) {
    page = parseInt(props.currentPage) + 1;
    if (props.currentPage == props.amountPages){
      page = props.amountPages;
    }
    href = "#" + page;
    return (
      <li className="page-item">
        <a className={classNameOfPageLink} href={href}>
          Next
        </a>
      </li>
    );
  } else {
    href = "#" + page;
    classNameOfPageLink = classNameOfPageLink + " " + page;
    return (
      <li className="page-item">
        <a className={classNameOfPageLink} href={href}>
          {page}
        </a>
      </li>
    );
  }
}
