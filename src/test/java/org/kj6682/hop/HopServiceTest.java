package org.kj6682.hop;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.kj6682.hop.Hop.*;
import static org.kj6682.hop.Hop.UNKNOWN_LOCATION;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Created by luigi on 12/04/2017.
 * <p>
 * This class is important because it defines the contract of HopService.
 * That is the way we want to use the methods of the HopService.
 * <p>
 * If we define this class before the implementation of the HopService, we are granted to have a better desing ;)
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HopServiceTest {

    List<Hop> fullList;
    List<Hop> partialList;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private HopRepository hopRepository;

    private HopService service;

    Pageable pageable = new Pageable() {
        @Override
        public int getPageNumber() {
            return 0;
        }

        @Override
        public int getPageSize() {
            return 0;
        }

        @Override
        public int getOffset() {
            return 0;
        }

        @Override
        public Sort getSort() {
            return null;
        }

        @Override
        public Pageable next() {
            return null;
        }

        @Override
        public Pageable previousOrFirst() {
            return null;
        }

        @Override
        public Pageable first() {
            return null;
        }

        @Override
        public boolean hasPrevious() {
            return false;
        }
    };
    Page page = new Page() {
        @Override
        public int getTotalPages() {
            return 1;
        }

        @Override
        public long getTotalElements() {
            return partialList.size();
        }

        @Override
        public Page map(Converter converter) {
            return null;
        }

        @Override
        public int getNumber() {
            return 0;
        }

        @Override
        public int getSize() {
            return partialList.size();
        }

        @Override
        public int getNumberOfElements() {
            return partialList.size();
        }

        @Override
        public List getContent() {
            return partialList;
        }

        @Override
        public boolean hasContent() {
            return true;
        }

        @Override
        public Sort getSort() {
            return null;
        }

        @Override
        public boolean isFirst() {
            return true;
        }

        @Override
        public boolean isLast() {
            return false;
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public boolean hasPrevious() {
            return false;
        }

        @Override
        public Pageable nextPageable() {
            return null;
        }

        @Override
        public Pageable previousPageable() {
            return null;
        }

        @Override
        public Iterator iterator() {
            return partialList.iterator();
        }
    };
    Page fullpage = new Page() {
        @Override
        public int getTotalPages() {
            return 1;
        }

        @Override
        public long getTotalElements() {
            return fullList.size();
        }

        @Override
        public Page map(Converter converter) {
            return null;
        }

        @Override
        public int getNumber() {
            return 0;
        }

        @Override
        public int getSize() {
            return fullList.size();
        }

        @Override
        public int getNumberOfElements() {
            return fullList.size();
        }

        @Override
        public List getContent() {
            return fullList;
        }

        @Override
        public boolean hasContent() {
            return true;
        }

        @Override
        public Sort getSort() {
            return null;
        }

        @Override
        public boolean isFirst() {
            return true;
        }

        @Override
        public boolean isLast() {
            return false;
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public boolean hasPrevious() {
            return false;
        }

        @Override
        public Pageable nextPageable() {
            return null;
        }

        @Override
        public Pageable previousPageable() {
            return null;
        }

        @Override
        public Iterator iterator() {
            return fullList.iterator();
        }
    };

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.service = new HopService();
        this.service.setHopRepository(this.hopRepository);

        fullList = new LinkedList<Hop>();
        partialList = new LinkedList<Hop>();

        for (int i = 0; i < 10; i++) {
            Hop h = new Hop("title " + i, "author " + i, "book", "nowhere");
            fullList.add(h);
            if (i % 2 == 0) {
                partialList.add(h);
            }
        }
    }

    @Test
    public void givenNullIdWhenFindingOneElementShouldThrowIllegalArgumentException() throws Exception {

        this.thrown.expect(IllegalArgumentException.class);
        this.thrown.expectMessage("A reasonable id is necessary when searching for one specific Hop");

        this.service.findOne(null);
    }

    @Test
    public void givenNullIdWhenDeletingAnElementShouldThrowIllegalArgumentException() throws Exception {

        this.thrown.expect(IllegalArgumentException.class);
        this.thrown.expectMessage("A reasonable id is necessary when searching for one specific Hop");

        this.service.deleteOne(null);
    }


    @Test
    public void givenUnknownIdWhenFindingOneElementShouldReturnADummyHop() throws Exception {
        Optional<Hop> optional = Optional.ofNullable(null);
        given(this.hopRepository.findById(anyLong())).willReturn(optional);

        Hop hop = this.service.findOne(-1L);

        assertThat(hop.getTitle()).isEqualTo(UNKNOWN_TITLE);
        assertThat(hop.getAuthor()).isEqualTo(UNKNOWN_AUTHOR);
        assertThat(hop.getType()).isEqualTo(UNKNOWN_TYPE);
        assertThat(hop.getLocation()).isEqualTo(UNKNOWN_LOCATION);
    }

    @Test
    public void givenIdShouldReturnAValidHop() throws Exception {
        Optional<Hop> optional = Optional.of(new Hop("title", "author", "type", "location"));

        given(this.hopRepository.findById(anyLong())).willReturn(optional);

        Hop hop = this.service.findOne(1L);

        verify(this.hopRepository, atMost(1)).findById(anyLong());
        verifyNoMoreInteractions(this.hopRepository);

        assertThat(hop.getTitle()).isEqualTo("title");
        assertThat(hop.getAuthor()).isEqualTo("author");
        assertThat(hop.getType()).isEqualTo("type");
        assertThat(hop.getLocation()).isEqualTo("location");
    }


    @Test
    public void givenInvalidParameterWhenInsertingShouldThrowException() {
        this.thrown.expect(IllegalArgumentException.class);
        this.thrown.expectMessage("A reasonable title is necessary when creating a Hop");

        this.service.insertOne(null, null, null, null);

        verifyNoMoreInteractions(this.hopRepository);

    }

    @Test
    public void insertShouldReturnTheElementWithItsId() {
        given(this.hopRepository.save(any())).willAnswer(RETURNS_MOCKS);

        Hop hop = this.service.insertOne("title", "author", "type", "location");

        verify(this.hopRepository, atMost(1)).save(any());
        verifyNoMoreInteractions(this.hopRepository);

        assertThat(hop).isNotNull();
        assertThat(hop.getId()).isNotNull();

    }

    @Test
    public void deleteShouldReturnSilently() {
        doNothing().when(this.hopRepository).delete(anyLong());

        this.service.deleteOne(anyLong());

        verify(this.hopRepository, atMost(1)).delete(any());
        verifyNoMoreInteractions(this.hopRepository);

    }


    @Test
    public void givenSearchItemShouldReturnAPartialListOfHops() {
        given(this.hopRepository.findAll()).willReturn(fullList);
        given(this.hopRepository.searchByAuthorOrTitle(any())).willReturn(partialList);

        List<Hop> list = this.service.find("search4me", null);

        verify(this.hopRepository, never()).findAll();
        verify(this.hopRepository, atMost(1)).searchByAuthorOrTitle(any());
        verifyNoMoreInteractions(this.hopRepository);

        assertThat(list).isNotEmpty();
        assertThat(list.size()).isEqualTo(partialList.size());
        assertThat(list.size()).isNotEqualTo(fullList.size());
    }

    @Test
    public void givenPaginationAndSearchItemShouldReturnAPartialListOfHops() {

        given(this.hopRepository.findAll()).willReturn(null);
        given(this.hopRepository.searchByAuthorOrTitle(anyString())).willReturn(null);
        given(this.hopRepository.searchByAuthorOrTitle(anyString(), any())).willReturn(page);

        List<Hop> list = this.service.find("search4me", pageable);

        verify(this.hopRepository, atMost(1)).searchByAuthorOrTitle(anyString(), any());
        verifyNoMoreInteractions(this.hopRepository);

        assertThat(list).isNotEmpty();
        assertThat(list.size()).isEqualTo(partialList.size());
        assertThat(list.size()).isNotEqualTo(fullList.size());
    }

    @Test
    public void givenNothingShouldReturnTheWholeListOfHops() {
        given(this.hopRepository.findAll()).willReturn(fullList);

        List<Hop> list = this.service.find("", null);
        List<Hop> listNull = this.service.find(null, null);

        verify(this.hopRepository, atMost(2)).findAll();
        verifyNoMoreInteractions(this.hopRepository);

        assertThat(list).isNotEmpty();
        assertThat(list.size()).isEqualTo(fullList.size());
        assertThat(list.size()).isNotEqualTo(partialList.size());
        assertThat(listNull).isNotEmpty();
        assertThat(listNull.size()).isEqualTo(fullList.size());
        assertThat(listNull.size()).isNotEqualTo(partialList.size());
    }

    @Test
    public void givenPaginationShouldReturnTheFullListOfHops() {

        given(this.hopRepository.findAll()).willReturn(null);
        given(this.hopRepository.findAll(any())).willReturn(fullpage);

        given(this.hopRepository.searchByAuthorOrTitle(anyString())).willReturn(null);
        given(this.hopRepository.searchByAuthorOrTitle(anyString(), any())).willReturn(null);

        List<Hop> list = this.service.find("", pageable);
        List<Hop> listNull = this.service.find(null, pageable);

        verify(this.hopRepository, atMost(2)).findAll(any());
        verifyNoMoreInteractions(this.hopRepository);

        assertThat(list).isNotEmpty();
        assertThat(list.size()).isEqualTo(fullList.size());
        assertThat(list.size()).isNotEqualTo(partialList.size());
        assertThat(listNull).isNotEmpty();
        assertThat(listNull.size()).isEqualTo(fullList.size());
        assertThat(listNull.size()).isNotEqualTo(partialList.size());
    }
}
